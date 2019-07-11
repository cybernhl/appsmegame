package weking.lib.game.view.car;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import weking.lib.game.R;
import weking.lib.game.bean.CarBean;
import weking.lib.game.event.CarPDEvent;
import weking.lib.game.utils.GameUtil;
import weking.lib.utils.LogUtils;

/**
 * Created by Administrator on 2017/12/20.
 */

public class CrazyCarLayout extends RelativeLayout{

    private Context context;
    private LayoutInflater layoutInflater;
    private GameCrazyCarView gameCrazyCarView;

    private RelativeLayout rl_car_vision;
    private RelativeLayout rl_car_invision;

    private Animation game_vision , game_invision;

    private int srceenWidth,srceenHeight;

    private boolean isBegin;

    private int win_id;

    private int time = 0;

    private int vision = 0;

    private int unvision ;

    private  List<CarBean> crazyCarResult;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null; //两个timer必须配合使用

    public void setWin_id(int win_id) {
        this.win_id = win_id;
    }

    public CrazyCarLayout(Context context, AttributeSet attrs , List<CarBean> crazyCarResult) {
        super(context, attrs);
        this.context = context;
        this.crazyCarResult = crazyCarResult;
        layoutInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        srceenHeight = GameUtil.getWindowHeight(context);
        srceenWidth = GameUtil.getWindowWidth(context);
        unvision = srceenWidth;
        game_vision = AnimationUtils.loadAnimation(getContext(), R.anim.game_car_bg_vision);
        game_invision = AnimationUtils.loadAnimation(getContext(), R.anim.game_car_bg_invision);

        View view = layoutInflater.inflate(R.layout.game_layout_main_car,null);
        rl_car_vision = (RelativeLayout)view.findViewById(R.id.rl_car_vision);
        rl_car_invision = (RelativeLayout)view.findViewById(R.id.rl_car_invision);
        gameCrazyCarView = (GameCrazyCarView)view.findViewById(R.id.game_car_view);

        this.addView(view);

        gameCrazyCarView.initView(new GameCrazyCarView.AnimListener() {
            @Override
            public void onAnimationCarEnd() {

            }
        }, crazyCarResult);

    }

    private void initAnim(int times) {
        if(times>600){
            return;
        }
        LogUtils.d("time,pao="+times);
        if(times % 60 == 0){
            vision = 0;
            unvision = srceenWidth;
        }

        ObjectAnimator visionanimator = ObjectAnimator.ofFloat(rl_car_vision, "translationX", vision,vision - srceenWidth/60);
        visionanimator.setDuration(16);
        visionanimator.setInterpolator(new LinearInterpolator());
        visionanimator.start();

        ObjectAnimator unanimator = ObjectAnimator.ofFloat(rl_car_invision, "translationX", unvision , unvision- srceenWidth/60);
        unanimator.setDuration(16);
        unanimator.setInterpolator(new LinearInterpolator());
        unanimator.start();

        vision -= srceenWidth/60;
        unvision -= srceenWidth/60;
        if(unvision<0){
            unvision = srceenWidth-srceenWidth/60;
        }
        if(vision < -srceenWidth){
            vision = 0-srceenWidth/60;
        }
        LogUtils.d("跑道  times"+times+",vision="+vision+",unvision="+unvision);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void carSpeedGrade(CarPDEvent event){
        LogUtils.d("跑道  times"+event.getTimes());
        initAnim(event.getTimes());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void carSpeedGrade(CarShowFirstEvent event){
        gameCrazyCarView.showFirst(win_id);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }
}
