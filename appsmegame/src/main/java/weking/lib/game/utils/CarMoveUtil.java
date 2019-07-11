package weking.lib.game.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import weking.lib.game.bean.CarBean;
import weking.lib.game.bean.SpeedBean;
import weking.lib.game.bean.VBean;
import weking.lib.game.event.CarPDEvent;
import weking.lib.game.event.CarShowAccelerateEvent;
import weking.lib.game.event.CarShowZDXEvent;
import weking.lib.game.event.CarSpeedGradeEvent;
import weking.lib.game.event.CrazyCarEndGameEvent;
import weking.lib.game.event.CrazyCarGetDataEvent;
import weking.lib.game.view.car.CarShowFirstEvent;
import weking.lib.utils.LogUtils;

/**
 * Created by Administrator on 2017/12/23.
 */

public class CarMoveUtil {
    private List<CarBean> speed_data;

    private int srceenWidth;

    // 跑道速度
    public double accelerate_prop_v = srceenWidth/60;
    //第一名一帧速度
    private float first_v = 0;
    //第一名车辆ID
    private int first_car_id = 0;
    //前一帧第一名车辆ID
    private int old_first_car_id = 0;
    //终点线的位置
    private float zdx_x;
    //时间
    private float time = 0;
    // 次数
    private int times = 0;
    // 一帧毫秒数
    private int frame_time = 16;
    // 一分钟
    private int min_time = 960;
    // 每档加速屏幕百分比
    private double speed_width = 0.2;
    // 终点线出现的时间点
    private double location_zdx_time;
    // 总的时间
    private double all_time = 9600;

    private boolean isShowZDX = false;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null; //两个timer必须配合使用

    public CarMoveUtil (Context context , List<CarBean> speed_data){
        srceenWidth = GameUtil.getWindowWidth(context);
        this.speed_data = speed_data;
        this.accelerate_prop_v = srceenWidth / 60;
    }

    //判断是否有车辆相遇
    private void isFirstExceed(int other_car_id , double time ,int times){
        CarBean otherCar = speed_data.get(other_car_id);
            if(first_v < otherCar.getSpeed_list().get(times)){
                // 如果下一帧速度大于第一名速度
                float next_x ;
                if(times == 0){
                     next_x = otherCar.getX().get(0);
                }else {
                     next_x = otherCar.getX().get(times-1);
                }
                if(otherCar.getSpeed_list().get(times) - first_v + next_x > zdx_x){
                    // 如果下一帧的相对位移超过第一名距离则把第一名速度重新赋值
                    first_v = otherCar.getSpeed_list().get(times);
                    first_car_id = other_car_id;
                }
            }
    }

    private void isExceedFirst(CarBean carBean ,int other_car_id,int times){
        CarBean first_car = speed_data.get(other_car_id);
        if(other_car_id == first_car_id){
            if(first_car.getSpeed_list().get(times) < carBean.getSpeed_list().get(times)){
                // 如果下一帧速度大于第一名速度
                float next_x ;
                if(times == 0){
                    next_x = carBean.getX().get(0);
                }else {
                    next_x = carBean.getX().get(times-1);
                }
                if(carBean.getSpeed_list().get(times) - first_v + next_x > zdx_x){
                    // 如果下一帧的相对位移超过第一名距离则把第一名速度重新赋值
                    first_v = carBean.getSpeed_list().get(times);
                    first_car_id = other_car_id;
                }
            }
        }

    }

    // 编辑加速数据
    public void getTime(float zdx , int location_zdx){
        this.zdx_x = zdx;
        this.location_zdx_time = all_time-(srceenWidth - location_zdx)/accelerate_prop_v;
        for(CarBean carBean : speed_data){
            for(int i = 0 ; i < 601  ; i ++){
                carBean.getX().add(zdx);
            }
        }
        for(int i = 0 ; i < 601  ; i ++){
            getV(i);
            getFirst(i);
            getX(i);
            getProp(i);
        }
        EventBus.getDefault().post(new CrazyCarGetDataEvent(speed_data));
    }


    // 获得每一帧的加速数据
    private void getV(int i){
        for(int car_id = 0 ; car_id < speed_data.size() ; car_id++){
            CarBean car = speed_data.get(car_id);
            boolean isHave = false;
            for(int grade_id = 0; grade_id < car.getCar().size() ; grade_id++){
                SpeedBean speed = car.getCar().get(grade_id);
                //判断是否在加速中i
                if(i*16 >= speed.getPlace_time() * min_time && i*16 < (speed.getPlace_time()+2)*min_time){
                    isHave = true;
                    car.getSpeed_list().add((float)(speed.getSpeed_grade()*srceenWidth*speed_width/120));
                }
            }
            if(!isHave){
                car.getSpeed_list().add((float)0);
            }
        }
    }

    private void getProp(int i){
        for(int car_id = 0 ; car_id < speed_data.size() ; car_id++){
            CarBean car = speed_data.get(car_id);
            VBean vBean = new VBean();
            for(int grade_id = 0; grade_id < car.getCar().size() ; grade_id++){
                SpeedBean speed = car.getCar().get(grade_id);
                getAccelerateTime(car,i,speed,vBean,car_id);
            }
            car.getTime_list().add(vBean);
        }
    }

    // 计算加速道具的时间
    private void getAccelerateTime(CarBean car , int i , SpeedBean speed ,VBean vBean,int car_id){

        double time = (srceenWidth - car.getX().get(i))/accelerate_prop_v;

        if(i * frame_time + time * frame_time  >= speed.getPlace_time() * min_time){
            if(speed.isShow()){
                speed.setShow(false);
                if(speed.getSpeed_grade()>0){
//                    LogUtils.d("getTime  time="+time*frame_time+",start_time="+i*frame_time+",car_id="+car_id+",i="+i);
                    vBean.setSpeed_state(VBean.SPEED_STATE_ACCELERATE);
                }else if(speed.getSpeed_grade() < 0){
                    vBean.setSpeed_state(VBean.SPEED_STATE_DECELERATE);
                }
                vBean.setStart_time(i*frame_time);
                vBean.setTime(time*frame_time);
            }
        }
    }
    // 计算下一帧第一名车辆
    private void getFirst(int i){
        for(int car_id = 0; car_id < speed_data.size(); car_id++){
            CarBean carBean = speed_data.get(car_id);
            boolean isFirst = false;
            if(first_car_id == car_id){
                //判断是否是第一名
                isFirst = true;
                first_v = carBean.getSpeed_list().get(i);
            }
            for(int other_car_id = 0 ; other_car_id < speed_data.size() ; other_car_id++){
                if(other_car_id != car_id){
                    if(isFirst){
                        isFirstExceed(other_car_id,time,i);
                    }else {
                        if(other_car_id == first_car_id){
                            isExceedFirst(carBean,other_car_id,i);
                        }
                    }
                }
            }
        }
    }

    // 计算下一帧偏移量
    private void getX(int i){

        for(int car_id = 0; car_id < speed_data.size(); car_id++ ){
            CarBean carBean = speed_data.get(car_id);
            float last_v;
            if(i == 0){
                last_v = zdx_x;
            }else {
                last_v = carBean.getX().get(i-1);
            }
            if(old_first_car_id == first_car_id){
                if(car_id != first_car_id){
                    carBean.getX().set(i,last_v-first_v+carBean.getSpeed_list().get(i));
                }
            }else {
                if(first_car_id == car_id){
                    carBean.getX().set(i,zdx_x);
                }else {
                    carBean.getX().set(i,last_v-first_v+ carBean.getSpeed_list().get(i));
                }
            }
//            LogUtils.d("getX   x="+carBean.getX().get(i)+",car_id="+car_id+",first_v="+first_v+",last_v="+last_v+",next_v"+carBean.getSpeed_list().get(i));
        }
        old_first_car_id = first_car_id;
    }

    public void begin(){
        startTime();
    }

    private void startTime(){
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                time = time + frame_time ;
//                LogUtils.d("赛车  mTimer times  " + times);
                Message message = mHandler.obtainMessage();
                message.arg1 = times;
                mHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,0,frame_time);

    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(times > 600){
                if(mTimer!=null){
                    mTimer.cancel();
                }
                return;
            }
            EventBus.getDefault().post(new CarPDEvent(times));
            speedGrade(times);
            LogUtils.d("赛车  mHandler times  " + times);
            if(times == 600){
                LogUtils.d("赛车  times停止  " + times);
                if(mTimer!=null){
                    mTimer.cancel();
                }
                EventBus.getDefault().post(new CrazyCarEndGameEvent());
                return;
            }
            times++;

        };
    };

    private void speedGrade(int times){
        if(speed_data == null){
            return;
        }
        if(times>600){
            return;
        }
        LogUtils.d("time,car="+times);
        for(int car_id = 0 ; car_id< speed_data.size() ; car_id++){
            CarBean carBean = speed_data.get(car_id);
            //加速道具
            if(carBean.getTime_list().size()!=0){
                if(carBean.getTime_list().get(times).getSpeed_state()== VBean.SPEED_STATE_ACCELERATE){
//                    LogUtils.d("getCarTime  time="+carBean.getTime_list().get(times).getTime()+",start_time="+times*16+",car_id = "+car_id+",i="+times);
                    EventBus.getDefault().post(new CarShowAccelerateEvent(carBean.getX().get(times),car_id,
                            carBean.getTime_list().get(times).getTime(),true));
                }else if(carBean.getTime_list().get(times).getSpeed_state()== VBean.SPEED_STATE_DECELERATE){
                    EventBus.getDefault().post(new CarShowAccelerateEvent(carBean.getX().get(times),car_id,
                            carBean.getTime_list().get(times).getTime(),false));
                }
            }
            // 车的位移
            float qd ;
            if(times == 0){
                qd = carBean.getX().get(0);
            }else {
                qd = carBean.getX().get(times-1);
            }
            float zd = carBean.getX().get(times);
            EventBus.getDefault().post(new CarSpeedGradeEvent(car_id,zd,qd));
        }
        // 终点线
        if(times * 16 >= location_zdx_time ){
            if(!isShowZDX){
                isShowZDX = true;
                EventBus.getDefault().post(new CarShowZDXEvent((int)(9600-location_zdx_time)*16));
            }
        }
        if(times == 600){
            EventBus.getDefault().post(new CarShowFirstEvent());
        }
    }


    public void release(){
        speed_data = null;
        if(mTimer!=null){
            mTimer.cancel();
            mTimer = null;
        }
        mTimerTask = null;
    }





}
