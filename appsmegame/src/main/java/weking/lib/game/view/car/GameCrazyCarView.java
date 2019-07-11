package weking.lib.game.view.car;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import weking.lib.game.R;
import weking.lib.game.bean.CarBean;
import weking.lib.game.bean.CrazyCarResult;
import weking.lib.game.event.CarShowZDXEvent;
import weking.lib.game.event.CrazyCarGetDataEvent;
import weking.lib.game.utils.CarMoveUtil;
import weking.lib.game.utils.GameUtil;
import weking.lib.utils.LogUtils;

/**
 * Created by Administrator on 2017/12/20.
 */

public class GameCrazyCarView extends RelativeLayout{

    private Context context;
    private LayoutInflater layoutInflater;
    private CrazyCarItemView crazyCarItemView1;
    private CrazyCarItemView crazyCarItemView2;
    private CrazyCarItemView crazyCarItemView3;

    private ImageView iv_car_qpx;
    private ImageView iv_car_zdx;
    private ProgressBar pb_time;
    private ProgressBar pb_first;

    private Animation game_qpx_anim;

    private AnimListener animListener;

    private int srceenWidth,srceenHeight;

    private CrazyCarResult crazyCarResult;

    private CarMoveUtil carMoveUtil;

    public GameCrazyCarView(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void initView(final AnimListener animListener , List<CarBean> mcrazyCarResult){
        EventBus.getDefault().register(this);

//        crazyCarResult = new CrazyCarResult();
//        List<CarBean> carBeanList = new ArrayList<CarBean>();
//        CarBean carBean = new CarBean();
//        List<SpeedBean> speedBeanList = new ArrayList<SpeedBean>();
//        SpeedBean speedBean = new SpeedBean();
//        Random random = new Random();
//        speedBean.setPlace_time(random.nextInt(2)+1);
//        speedBean.setSpeed_grade(random.nextInt(3)+1);
////        speedBean.setPlace_time(4);
////        speedBean.setSpeed_grade(1);
////
//        SpeedBean speedBean1 = new SpeedBean();
//        speedBean1.setPlace_time(random.nextInt(3)+4);
//        speedBean1.setSpeed_grade(random.nextInt(3)+1);
//
//        SpeedBean speedBean2 = new SpeedBean();
//        speedBean2.setPlace_time(random.nextInt(2)+7);
//        speedBean2.setSpeed_grade(random.nextInt(3)+1);
////        speedBean2.setPlace_time(7);
////        speedBean2.setSpeed_grade(-1);
//
//        speedBeanList.add(speedBean);
//        speedBeanList.add(speedBean1);
//        speedBeanList.add(speedBean2);
//        carBean.setCar(speedBeanList);
//
//        CarBean carBean1 = new CarBean();
//        List<SpeedBean> speedBeanList1 = new ArrayList<SpeedBean>();
//        SpeedBean speedBean3 = new SpeedBean();
//        speedBean3.setPlace_time(random.nextInt(2)+1);
//        speedBean3.setSpeed_grade(random.nextInt(3)+1);
////        speedBean3.setPlace_time(4);
////        speedBean3.setSpeed_grade(-1);
//
//        SpeedBean speedBean14 = new SpeedBean();
//        speedBean14.setPlace_time(random.nextInt(3)+4);
//        speedBean14.setSpeed_grade(random.nextInt(3)+1);
////        speedBean14.setPlace_time(6);
////        speedBean14.setSpeed_grade(3);
//
//        SpeedBean speedBean5 = new SpeedBean();
//        speedBean5.setPlace_time(random.nextInt(2)+7);
//        speedBean5.setSpeed_grade(random.nextInt(3)+1);
//
//
//
//        speedBeanList1.add(speedBean3);
//        speedBeanList1.add(speedBean14);
//        speedBeanList1.add(speedBean5);
//        carBean1.setCar(speedBeanList1);
//
//        CarBean carBean2 = new CarBean();
//        List<SpeedBean> speedBeanList2 = new ArrayList<SpeedBean>();
//        SpeedBean speedBean6 = new SpeedBean();
//        speedBean6.setPlace_time(random.nextInt(2)+1);
//        speedBean6.setSpeed_grade(random.nextInt(3)+1);
////        speedBean6.setPlace_time(2);
////        speedBean6.setSpeed_grade(2);
//
//        SpeedBean speedBean7 = new SpeedBean();
//        speedBean7.setPlace_time(random.nextInt(3)+4);
//        speedBean7.setSpeed_grade(random.nextInt(3)+1);
////        speedBean7.setPlace_time(4);
////        speedBean7.setSpeed_grade(-1);
//
//        SpeedBean speedBean8 = new SpeedBean();
//        speedBean8.setPlace_time(random.nextInt(2)+7);
//        speedBean8.setSpeed_grade(random.nextInt(3)+1);
////        speedBean8.setPlace_time(7);
////        speedBean8.setSpeed_grade(2);
//
//        speedBeanList2.add(speedBean6);
//        speedBeanList2.add(speedBean7);
//        speedBeanList2.add(speedBean8);
//
//        carBean2.setCar(speedBeanList2);
//        LogUtils.d("随机=="+"a:"+speedBean.getPlace_time()+","+speedBean.getSpeed_grade()+";;;"
//                +speedBean1.getPlace_time()+","+speedBean1.getSpeed_grade()+";;;"
//                +speedBean2.getPlace_time()+","+speedBean2.getSpeed_grade()+";;;");
//        LogUtils.d("随机=="+"b:"+speedBean3.getPlace_time()+","+speedBean3.getSpeed_grade()+";;;"
//                +speedBean14.getPlace_time()+","+speedBean14.getSpeed_grade()+";;;"
//                +speedBean5.getPlace_time()+","+speedBean5.getSpeed_grade()+";;;");
//        LogUtils.d("随机=="+"c:"+speedBean6.getPlace_time()+","+speedBean6.getSpeed_grade()+";;;"
//                +speedBean7.getPlace_time()+","+speedBean7.getSpeed_grade()+";;;"
//                +speedBean8.getPlace_time()+","+speedBean8.getSpeed_grade()+";;;");
//        carBeanList.add(carBean);
//        carBeanList.add(carBean1);
//        carBeanList.add(carBean2);
//        crazyCarResult.setSpeed_data(carBeanList);

        carMoveUtil = new CarMoveUtil(context,mcrazyCarResult);

        this.animListener = animListener;
        srceenHeight = GameUtil.getWindowHeight(context);
        srceenWidth = GameUtil.getWindowWidth(context);
        View view = layoutInflater.inflate(R.layout.game_car_view,null);
        crazyCarItemView1 = (CrazyCarItemView)view.findViewById(R.id.game_car_1);
        crazyCarItemView2 = (CrazyCarItemView)view.findViewById(R.id.game_car_2);
        crazyCarItemView3 = (CrazyCarItemView)view.findViewById(R.id.game_car_3);
        iv_car_zdx = (ImageView)view.findViewById(R.id.iv_car_zdx);
        iv_car_qpx = (ImageView)view.findViewById(R.id.iv_car_qpx);
        pb_time = (ProgressBar) view.findViewById(R.id.pb_time);
        pb_first = (ProgressBar) view.findViewById(R.id.pb_first);
        game_qpx_anim = AnimationUtils.loadAnimation(getContext(), R.anim.game_car_qpx);

        this.addView(view);

        int [] location_qpx = new int [2];
        iv_car_qpx.getLocationOnScreen(location_qpx);
        LogUtils.d("location======x="+location_qpx[0]+",y="+location_qpx[1]);
        LogUtils.d("location======iv_car_qpx.getWidth()="+iv_car_qpx.getWidth());
        crazyCarItemView1.initView(new CrazyCarItemView.AnimListener(){
            @Override
            public void onAnimationCarEnd(int[] location_car) {

                final int [] location_zdx = new int [2];
                iv_car_zdx.getLocationOnScreen(location_zdx);
                if(carMoveUtil!=null){
                    carMoveUtil.getTime(location_zdx[0]-location_car[0]- iv_car_zdx.getWidth()*2,location_zdx[0]);
                }
//                double location_zdx_time = carMoveUtil.all_time-(srceenWidth - location_zdx[0])/carMoveUtil.accelerate_prop_v;
//                crazyCarItemView1.setLocation_zdx_time(location_zdx_time);

            }
            @Override
            public void onAnimationCarToZDX(int zdx, int location_zdx) {
                if(carMoveUtil!=null){
                    carMoveUtil.begin();
                }
                qpxHide();
                animListener.onAnimationCarEnd();
            }
        },R.drawable.icon_yellowcar_s_game,mcrazyCarResult.get(2),2);
        crazyCarItemView2.initView( new CrazyCarItemView.AnimListener(){
            @Override
            public void onAnimationCarEnd(int[] location_car) {
                final int [] location_zdx = new int [2];
                iv_car_zdx.getLocationOnScreen(location_zdx);
//                double location_zdx_time = carMoveUtil.all_time-(srceenWidth - location_zdx[0])/carMoveUtil.accelerate_prop_v;
//                crazyCarItemView2.setLocation_zdx_time(location_zdx_time);
            }
            @Override
            public void onAnimationCarToZDX(int zdx, int location_zdx) {


            }
        },R.drawable.icon_redcar_s_game,mcrazyCarResult.get(1),1);
        crazyCarItemView3.initView(new CrazyCarItemView.AnimListener() {
            @Override
            public void onAnimationCarEnd(int[] location_car) {
                final int [] location_zdx = new int [2];
                iv_car_zdx.getLocationOnScreen(location_zdx);
//                double location_zdx_time = carMoveUtil.all_time-(srceenWidth - location_zdx[0])/carMoveUtil.accelerate_prop_v;
//                crazyCarItemView3.setLocation_zdx_time(location_zdx_time);
            }
            @Override
            public void onAnimationCarToZDX(int zdx , int location_zdx) {

            }
        },R.drawable.icon_blackcar_s_game,mcrazyCarResult.get(0),0);
        startEndTime();
    }

    //开始倒计时
    private void startEndTime(){
        CountDownTimer cdt = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                pb_time.setVisibility(GONE);
                int [] location_zdx = new int [2];
                iv_car_zdx.getLocationOnScreen(location_zdx);
                crazyCarItemView1.carToZDX(location_zdx ,iv_car_zdx.getWidth());
                crazyCarItemView2.carToZDX(location_zdx ,iv_car_zdx.getWidth());
                crazyCarItemView3.carToZDX(location_zdx ,iv_car_zdx.getWidth());
            }
        };
        cdt.start();
    }

    //隐藏起跑线
    private void qpxHide(){
        int [] location_qpx = new int [2];
        iv_car_qpx.getLocationOnScreen(location_qpx);
        int v = srceenWidth/60;
        int time = (location_qpx[0]+iv_car_qpx.getWidth())/v;
        LogUtils.d("qpx  =====  "+time+",==="+(location_qpx[0]+iv_car_qpx.getWidth()));
        ObjectAnimator qpxAnimator = ObjectAnimator.ofFloat(iv_car_qpx, "translationX", 0
                , -location_qpx[0]-iv_car_qpx.getWidth());
        qpxAnimator.setDuration(time*16);
        qpxAnimator.setInterpolator(new LinearInterpolator());
        qpxAnimator.start();
    }

    //显示终点线
    public void zdxShow(int time){
        int [] location_zdx = new int [2];
        iv_car_zdx.setVisibility(VISIBLE);
        iv_car_zdx.getLocationOnScreen(location_zdx);
        ObjectAnimator zdxAnimator = ObjectAnimator.ofFloat(iv_car_zdx,"translationX",srceenWidth-location_zdx[0],0);
        zdxAnimator.setDuration(time);
        zdxAnimator.setInterpolator(new LinearInterpolator());
        zdxAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }
            @Override
            public void onAnimationEnd(Animator animator) {

            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        zdxAnimator.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCarData(CrazyCarGetDataEvent event){
        crazyCarItemView1.setCarBean(event.getCarBeanList().get(0));
        crazyCarItemView2.setCarBean(event.getCarBeanList().get(1));
        crazyCarItemView3.setCarBean(event.getCarBeanList().get(2));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void carSpeedGrade(CarShowZDXEvent event){
        zdxShow(event.getTime());
    }


    public void showFirst(int win_id){
        int lValue =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
        LayoutParams layoutParams  = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if(win_id == 1){
            layoutParams.topMargin = lValue*2;
        }else if(win_id == 2){
            layoutParams.topMargin = lValue*1;
        }else {
            layoutParams.topMargin = lValue*0;
        }
        pb_first.setLayoutParams(layoutParams);
        pb_first.setVisibility(VISIBLE);
        CountDownTimer cdt = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                pb_first.setIndeterminateDrawable(getResources().getDrawable(
                        R.drawable.fst_13));
                pb_first.setProgressDrawable(getResources().getDrawable(
                        R.drawable.fst_13));
            }
        };
        cdt.start();
    }


    public interface AnimListener {
        void onAnimationCarEnd();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
        if(carMoveUtil!=null){
            carMoveUtil.release();
        }
        carMoveUtil = null;
    }
}
