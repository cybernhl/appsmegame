package weking.lib.game.view.car;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import weking.lib.game.R;
import weking.lib.game.bean.CarBean;
import weking.lib.game.event.CarShowAccelerateEvent;
import weking.lib.game.event.CarSpeedGradeEvent;
import weking.lib.game.utils.GameUtil;


/**
 * Created by Administrator on 2017/12/20.
 */

public class CrazyCarItemView extends BaseCarItemView{

    private Context context;
    private LayoutInflater layoutInflater;

    private ImageView car_1;
    private ProgressBar pb_accelerate_car;
    private ImageView iv_accelerate_prop;
    private RelativeLayout rl_car;

    private int [] location_qpx,location_zdx;

    private AnimListener animListener;

    private int srceenWidth,srceenHeight;

    private CarBean carBean;

    private int car_id;
    private int first_cation;

    private double location_zdx_time;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null; //两个timer必须配合使用


    private boolean isShowZDX = false;

    public void setLocation_zdx_time(double location_zdx_time) {
        this.location_zdx_time = location_zdx_time;
    }

    public void setCarBean(CarBean carBean) {
        this.carBean = carBean;
    }

    public CrazyCarItemView(@NonNull Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void initView(AnimListener animListener , int Car_ResID , CarBean crazyCarBean ,int car_id){
        EventBus.getDefault().register(this);
        this.animListener = animListener;
        this.carBean = crazyCarBean;
        this.car_id = car_id;
        srceenHeight = GameUtil.getWindowHeight(context);
        srceenWidth = GameUtil.getWindowWidth(context);

        View view = layoutInflater.inflate(R.layout.game_car_item_view,null);
        this.addView(view);

        car_1 = (ImageView)view.findViewById(R.id.car_1);
        car_1.setImageResource(Car_ResID);
        pb_accelerate_car = (ProgressBar) view.findViewById(R.id.pb_accelerate_car);
        iv_accelerate_prop = (ImageView)view.findViewById(R.id.iv_accelerate_prop);
        rl_car = (RelativeLayout) view.findViewById(R.id.rl_car);

        initAnim();
    }

    private void initAnim() {
        // 位移动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(rl_car, "translationX", -100, 0);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }
            @Override
            public void onAnimationEnd(Animator animator) {
                final int [] location_car = new int[2];
                rl_car.getLocationOnScreen(location_car);
                animListener.onAnimationCarEnd(location_car);
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public interface AnimListener {
        void onAnimationCarEnd(int[] location_car);

        void onAnimationCarToZDX(int zdx ,int location_zdx);
    }


    //加速道具
    private void accelerateProp(float count , double time, boolean isAccelerate){
        if(isAccelerate){
            iv_accelerate_prop.setImageResource(R.drawable.icon_nitrogen);
        }else {
            iv_accelerate_prop.setImageResource(R.drawable.icon_slowdown);
        }
        int v = srceenWidth/60;
        int times = (int) ((srceenWidth-(count+(rl_car.getWidth()/2)))/v);
        ObjectAnimator propAnimator = ObjectAnimator.ofFloat(iv_accelerate_prop,"translationX",srceenWidth,
                count+(rl_car.getWidth()/2));
        propAnimator.setDuration(times*16);
        propAnimator.setInterpolator(new LinearInterpolator());
        propAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                iv_accelerate_prop.setVisibility(VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                iv_accelerate_prop.setVisibility(INVISIBLE);
                pb_accelerate_car.setVisibility(VISIBLE);
                startTime();
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        propAnimator.start();
    }

    private void startTime(){
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = mHandler.obtainMessage();
                message.arg1 = 0;
                mHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,1920);

    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pb_accelerate_car.setVisibility(INVISIBLE);
            mTimer.cancel();
        };
    };

    // 驾驶到终点线
    public void carToZDX(final int [] location_zdx , final int zdx){
        final int [] location_car = new int[2];
        rl_car.getLocationOnScreen(location_car);
        ObjectAnimator carAnimator = ObjectAnimator.ofFloat(rl_car, "translationX", location_car[0] ,
                location_zdx[0]-location_car[0]- zdx*2);
        carAnimator.setDuration(1000);
        carAnimator.setInterpolator(new LinearInterpolator());
        carAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }
            @Override
            public void onAnimationEnd(Animator animator) {
                if(animListener != null){
                    animListener.onAnimationCarToZDX(location_zdx[0]-location_car[0]- zdx*2,location_zdx[0]);
                }
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        carAnimator.start();
    }

//    public void carSpeedGrade(final int times){
//        if(times>600){
//            return;
//        }
//        LogUtils.d("time,car="+times);
//        //加速道具
//        if(carBean.getTime_list().get(times).getSpeed_state()== VBean.SPEED_STATE_ACCELERATE){
//            LogUtils.d("getCarTime  time="+carBean.getTime_list().get(times).getTime()+",start_time="+times*16+",car_id = "+car_id+",i="+times);
//            accelerateProp(carBean.getX().get(times),
//                    carBean.getTime_list().get(times).getTime(),true);
//        }else if(carBean.getTime_list().get(times).getSpeed_state()== VBean.SPEED_STATE_DECELERATE){
//            accelerateProp(carBean.getX().get(times),
//                    carBean.getTime_list().get(times).getTime(),false);
//        }
//
//        // 终点线
//        if(times * 16 >= location_zdx_time ){
//            if(!isShowZDX){
//                isShowZDX = true;
//                EventBus.getDefault().post(new CarShowZDXEvent((int)(9600-location_zdx_time)*16));
//            }
//        }
//
//        // 车的位移
//        float qd ;
//        if(times == 0){
//            qd = carBean.getX().get(0);
//        }else {
//            qd = carBean.getX().get(times-1);
//        }
//        float zd = carBean.getX().get(times);
//        ObjectAnimator propAnimator = ObjectAnimator.ofFloat(rl_car,"translationX",qd
//                ,zd);
//        propAnimator.setDuration(16);
//        propAnimator.setInterpolator(new LinearInterpolator());
//        propAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                int finalTimes = times;
//                finalTimes++;
//                if(finalTimes <= carBean.getX().size()){
//                    carSpeedGrade(finalTimes);
//                }else {
//                    EventBus.getDefault().post(new CarShowFirstEvent());
//                }
//            }
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//        propAnimator.start();
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void carSpeedGrade(CarSpeedGradeEvent event){
        if(event.getCar_id()==car_id){
            ObjectAnimator propAnimator = ObjectAnimator.ofFloat(rl_car,"translationX",event.getCount()
                    ,event.getSpeed_grade());
            propAnimator.setDuration(16);
            propAnimator.setInterpolator(new LinearInterpolator());
            propAnimator.start();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void carAccelerate(CarShowAccelerateEvent event){
        if(event.getCar_id() == car_id){
            accelerateProp(event.getCount(),event.getTime(),event.getIsAccelerate());
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }
}
