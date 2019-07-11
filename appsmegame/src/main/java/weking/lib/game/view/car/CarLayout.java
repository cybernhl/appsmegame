package weking.lib.game.view.car;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.bean.AllBetPush;
import weking.lib.game.bean.CarBean;
import weking.lib.game.bean.SpeedBean;
import weking.lib.game.bean.push.CarEndPush;
import weking.lib.game.event.CarDataEvent;
import weking.lib.game.event.CrazyCarEndGameEvent;
import weking.lib.game.event.PublishCarDataEvent;
import weking.lib.game.observer.GameObserver;
import weking.lib.utils.LogUtils;


/**
 * Created by Administrator on 2017/12/27.
 */

public class CarLayout extends BaseCarLayout{

    private RelativeLayout game_view;

    private CrazyCarLayout crazyCarLayout;

    public CarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarLayout(Context context, boolean isPublish) {
        super(context, null);
        this.isPublish = isPublish;
        init(context);
    }

    public void init(Context context){
        this.context = context;
        EventBus.getDefault().register(this);
        LayoutInflater.from(context).inflate(R.layout.game_layout_car_bet, this);
        game_view = (RelativeLayout) findViewById(R.id.game_view);
        super.init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCarData(CarDataEvent event){
        if(game_state==GAME_STATE_SAI_CHE){
            return;
        }
        win_id = event.getCarDataPush().getWin_id();
        if(!isPublish){
            setAllCarData(event.getCarDataPush().getSpeed_data());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setPubCarData(PublishCarDataEvent event){
        if(game_state==GAME_STATE_SAI_CHE){
            return;
        }
        win_id = event.getWin_id();
        setAllCarData(event.getList());
    }

    private void setAllCarData(List<CarBean> carData){
        game_state = GAME_STATE_SAI_CHE;
        LogUtils.d("赛车   开始游戏  win_id="+win_id);
        crazyCarLayout = new CrazyCarLayout(context,null,carData);
        for(int i = 0; i<carData.size();i++){
            for(SpeedBean speedBean : carData.get(i).getCar()){
                LogUtils.d("赛车   数据 :"+i+":"+speedBean.getSpeed_grade()
                        +","+speedBean.getPlace_time());
            }
        }
        crazyCarLayout.setWin_id(win_id);
        game_view.addView(crazyCarLayout);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gameEnd(CrazyCarEndGameEvent event){
        mHandler.postDelayed(endCar,2000);
    }

    Runnable endCar = new Runnable() {
        @Override
        public void run() {
            showEndView();
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBetEvent(AllBetPush allBetPush) {
        setAllBetText(allBetPush);
    }

    private void showEndView(){
        if(game_state == GAME_STATE_END){
            return;
        }
        game_state = GAME_STATE_END;
        LogUtils.d("赛车   结束游戏");
        if(isPublish){
            mPresenter.endGame(getGameType());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCarEndEvent(CarEndPush carEndPush) {
        mHandler.postDelayed(endGame,8000);
        win_id = carEndPush.getWin_id();
        iv_sai_che.setVisibility(GONE);
        LogUtils.d("赛车   推送结束游戏 win_id = "+win_id);
        game_car_end_view.setVisibility(VISIBLE);
        iv_end.setVisibility(VISIBLE);
        game_view.removeView(crazyCarLayout);
        tv_end.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Aku&Kamu.otf"));
        tv_end.setText(GameObserver.getAppObserver().getStringText(GameC.str.CRAZY_CAR_END));
        switch (win_id){
            case 1:
                pb_black_car_end.setVisibility(VISIBLE);
                break;
            case 2:
                pb_red_car_end.setVisibility(VISIBLE);
                break;
            case 3:
                pb_yellow_car_end.setVisibility(VISIBLE);
                break;
            default:
        }
    }


}
