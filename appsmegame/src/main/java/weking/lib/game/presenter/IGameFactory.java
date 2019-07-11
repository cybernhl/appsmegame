package weking.lib.game.presenter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import weking.lib.game.GameC;
import weking.lib.game.bean.AllBetPush;
import weking.lib.game.bean.BetRespond;
import weking.lib.game.bean.GameKaiPaiPush;
import weking.lib.game.bean.JoinGameRespond;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.manager.GameSound;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.GameUtil;

/**
 * 创建时间 2017/6/16.
 * 创建人 frs
 * 功能描述
 */
public abstract class IGameFactory  extends RelativeLayout
        implements View.OnClickListener, IGame , GameContract.View  {
    protected Context mContext;
    protected Typeface mTypeFace;
    protected GameContract.Presenter mPresenter;
    protected int mMy_diamonds = 0;
    protected Handler mHandler;
    protected TextView tv_money;


    protected void setMoney(int my_diamonds) {
        if (my_diamonds < 0) {
            my_diamonds = 0;
        }
        this.mMy_diamonds = my_diamonds;
        String key = GameObserver.getAppObserver().getStringText(GameC.str.MY_DIAMONDS);
        GameObserver.getAppObserver().setObject(key, Integer.valueOf(my_diamonds));
        tv_money.setTypeface(mTypeFace);
        this.tv_money.setText(GameUtil.formatBetText(this.mMy_diamonds));
    }


    public IGameFactory(Context context) {
        super(context);
    }

    public IGameFactory(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPresenter = new GamePresenter(this);
        mContext = context;
        mHandler = new Handler();
        mTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/game_poker_text_type.ttf");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mHandler = null;
        }
    }


    @Override
    public void sendGiftResult(int my_diamonds) {

    }

    @Override
    public void betResult(BetRespond betRespond) {

    }

    @Override
    public void showKaiPaiPush(GameKaiPaiPush result) {

    }

    @Override
    public void showStartGameError() {

    }

    @Override
    public void showXiaZhuPush(XiaZhuPush result) {

    }

    @Override
    public void showendGameError() {

    }

    @Override
    public void setTopic(String topic) {

    }

    @Override
    public void startGame() {

    }

    /**
     * 下注阶段
     *
     * @param gameSound
     * @param xiaZhuPush
     */
    @Override
    public void doXiaZhu(GameSound gameSound, XiaZhuPush xiaZhuPush) {

    }

    /**
     * 显示下注总信息
     *
     * @param allBetPush
     */
    @Override
    public void setAllBetText(AllBetPush allBetPush) {

    }

    /**
     * 显示自己下注
     *
     * @param who    哪手牌
     * @param myBet  下多少注
     * @param allBet 这手牌总注
     */
    @Override
    public void setMyBetText(int who, int myBet, int allBet) {

    }

    /**
     * 显示一副牌的下注数
     *
     * @param who    哪手牌
     * @param number 自己的注
     * @param allBet 所有的注数
     */
    @Override
    public void setOneBetText(int who, int number, int allBet) {

    }

    /**
     * 开牌阶段
     *
     * @param gamePush
     * @param gameSound
     */
    @Override
    public void doKaiPai(GameKaiPaiPush gamePush, GameSound gameSound) {

    }

    /**
     * 显示我的钻石
     *
     * @param diamonds
     */
    @Override
    public void setMoneyTextView(int diamonds, long delayed) {

    }

    @Override
    public void sendGift(int giftId, int giftType) {

    }

    /**
     * 下注阶段，加入游戏
     *
     * @param joinGameRespond
     */
    @Override
    public void joinGameXiaZhu(JoinGameRespond joinGameRespond) {

    }

    /**
     * 开牌阶段，加入游戏
     *
     * @param gameRespond
     */
    @Override
    public void joinGameKaiPai(JoinGameRespond gameRespond) {

    }

    /**
     * Set the enabled state of this VIEW.
     *
     * @param visibility One of VISIBLE, INVISIBLE or #GONE.
     */
    @Override
    public void setVisibility(int visibility) {

    }

    @Override
    public void startGameDollData(List<List<XiaZhuPush.GameBaen>> doll_game) {
    }

    @Override
    public void startGamestar_warsData(List<XiaZhuPush.GameBaen> star_wars) {
    }

    @Override
    public void startGame(int gameType) {
        mPresenter.startGame(gameType);
    }

}

