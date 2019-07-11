package weking.lib.game.view.game_2;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.GameC;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.manager.memory.GameSoundZww;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.presenter.IGameFactory;


public abstract class BaseGame_2Layout extends IGameFactory {
    protected View view_toolbar;
    protected ImageView iv_money;
    protected ImageView iv_bet10;
    protected ImageView iv_bet100;
    protected ImageView iv_bet1000;
    protected int currentBet = 10;
    public static int bet10 = 10;
    public static int bet100 = 100;
    public static int bet1000 = 1000;
    public static int bet5000 = 5000;
    protected boolean isPublish; //是否主播
    protected boolean isinitMoneyBet;//是否刷新金币
    //    protected Game2Preserter mPresenter;
    protected ArrayList<Integer> mapIndex = new ArrayList();
    protected ArrayList<Integer> mapIndex10 = new ArrayList();
    protected ArrayList<Integer> mapIndex100 = new ArrayList();
    protected ArrayList<Integer> mapIndex1000 = new ArrayList();
    protected GameSoundZww mGameSound; //背景缓存
    protected boolean isOpneGameBgSound; // 是否打开

    public BaseGame_2Layout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isPublish) {
            mGameSound = new GameSoundZww(mContext);
            mGameSound.initSoundPool();
            isOpneGameBgSound = (boolean) GameObserver.getAppObserver().getObject(GameObserver.getAppObserver().getStringText(GameC.str.IS_GAME_BG_SOUND), false);
        }

    }


    @Override
    public void setMoneyTextView(final int diamonds, long delayed) {
        isinitMoneyBet = true;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setMoney(diamonds);
            }
        }, delayed);
    }

    @Override
    public void startGameDollData(List<List<XiaZhuPush.GameBaen>> doll_game) {
    }

    @Override
    public void startGamestar_warsData(List<XiaZhuPush.GameBaen> star_wars) {
    }

    @Override
    public void startGame() {
        this.mPresenter.startGame(getGameType());
    }

    public abstract int getGameType();

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mGameSound != null) {
            mGameSound.soundDestroy();
            mGameSound = null;
        }
    }

    @Override
    public void showCarDataError() {

    }
}



