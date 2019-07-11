package weking.lib.game.base;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import app.io.weking.anim.bean.Gift;
import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.bean.AllBetPush;
import weking.lib.game.bean.BetRespond;
import weking.lib.game.bean.GameItemInfo;
import weking.lib.game.bean.GameKaiPaiPush;
import weking.lib.game.bean.JoinGameRespond;
import weking.lib.game.bean.NextBenkerPush;
import weking.lib.game.bean.Poker;
import weking.lib.game.bean.StartLiveResult;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.bean.push.GameResultPush;
import weking.lib.game.dialog.MyBaseTipDialog;
import weking.lib.game.event.GoPayListActivityEvent;
import weking.lib.game.event.RefreshGamePanelMyDiamondsEvent;
import weking.lib.game.manager.GameSound;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.GameAction;
import weking.lib.game.utils.GameAnimUtil;
import weking.lib.game.utils.GameUtil;
import weking.lib.utils.LogUtils;


public abstract class BaseGameLayout extends BaseGamePLayout {
    private static final String TAG = "BaseGameLayout";
    protected int game_state = GAME_STATE_FA_PAI;
    protected static final int GAME_STATE_FA_PAI = 0;
    protected static final int GAME_STATE_XIA_ZHU = 1;
    protected static final int GAME_STATE_KAI_PAI = 2;
    protected static final int GAME_STATE_FA_JIANG = 3;
    public static final int BET_10 = 10;
    public static final int BET_100 = 100;
    public static final int BET_1000 = 1000;
    public static final int BET_5000 = 5000;
    public static final int BET_LEFT = 1;
    public static final int BET_CENTER = 2;
    public static final int BET_RIGHT = 3;
    public static final int time_tip_delay = 3500;
    public static final int time_win_delay = 14000;
    protected Context mApp;
    protected int whoWin = -1;
    protected boolean isPublish = false;


    protected int currentBet = 10;
    protected ImageView card_fapai;
    protected ImageView tishi;
    protected ImageView tishi_bg;

    protected View chongzhi;
    protected ImageView IV_bet10;
    protected ImageView IV_bet100;
    protected ImageView IV_bet1000;
    protected ImageView IV_bet5000;
    protected TextView bet_left_all;
    protected TextView bet_ping_all;
    protected TextView bet_user_all;
    protected TextView bet_left_me;
    protected TextView bet_ping_me;
    protected TextView bet_right_me;
    protected View rl_bet_left;
    protected View rl_bet_ping;
    protected View rl_bet_right;
    protected View rl_bet_banker;
    protected TextView tv_bei_shu_left;
    protected TextView tv_bei_shu_ping;
    protected TextView tv_bei_shu_right;
    protected View view_tip;
    protected ViewGroup root_container;
    protected GameSound mGameSound;
    //    protected GameContract.Presenter mPresenter;
    private String topic = "";
    // 输赢的钱
    protected ArrayList<Integer> mWin_money_position;
    //自己赢得钱
    protected int mMyWinMoney;
    // 庄家的开销
    protected int mBanker_win_num;

    protected void init() {
        this.mApp = GameObserver.getAppObserver().getApp();
//        this.mPresenter = new GamePresenter(this);
        this.mGameSound = new GameSound(this.mApp.getApplicationContext());
        this.mGameSound.initSoundPool();
        registerReceiver();
    }

    @Override
    public void startGame() {
        if (this.isPublish) {
            this.mPresenter.startGame(getGameType());
        }
    }

    @Override
    public void setTopic(String topic) {
        this.topic = topic;
    }

    protected void endGame() {
        if (this.isPublish) {
            this.mPresenter.endGame(getGameType());
        }
    }

    public BaseGameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected int timeDJS = 32;

    protected abstract int getGameType();

    @Override
    public void doXiaZhu(final GameSound gameSound, XiaZhuPush xiaZhuPush) {
    }


    protected abstract void showFapaiCard();

    /**
     * 等待下一局
     *
     * @param gameResultPush
     */
    protected void doGameResult(final GameResultPush gameResultPush) {

    }


    @Override
    public void doKaiPai(GameKaiPaiPush gamePush, GameSound gameSound) {

    }

    protected void doStartGameAction(int countdown_time) {
        if (this.isPublish) {
            this.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BaseGameLayout.this.startGame();
                }
            }, countdown_time * 1000 - 1000 < 0 ? 0L : countdown_time * 1000 - 1000);
        }
    }

    protected void showShouPaiView() {
        showFapaiCard();
    }

    protected void winOrLoseTip(int winMoney, List<Gift> gifts, GameSound gameSound, long delayed) {
        if (winMoney > 0) {
            winTip(winMoney, gifts, gameSound);
            showTishiImg(GameC.img.game_yxjjks);
        } else if (winMoney == 0) {
            showTishiImg(GameC.img.game_yxjjks);
        } else {
            gameSound.playSound(4, 0);
            showTishiImg(GameC.img.game_jyxcbz);
        }
        tipStarGame(delayed);
    }

    protected abstract void showTishiImg(String paramString);

    protected void winTip(int winMoney, List<Gift> gifts, GameSound gameSound) {
        winTip(winMoney, gifts, gameSound, true);
    }

    protected void winTip(int winMoney, List<Gift> gifts, GameSound gameSound, boolean isShouAnim) {
        if (isPublish) {
            gifts.clear();
        }
        if (isShouAnim) {
            gameSound.playSound(2, 0);
            gameSound.playSound(3, 0);
        }
        showWinDialog(winMoney, gifts, isShouAnim);

    }

    protected abstract void showWinDialog(int paramInt, List<Gift> paramList, boolean isShouAnim);

    public void tipStarGame(final long delayed) {
        this.view_tip.bringToFront();
        this.tishi_bg.setVisibility(View.VISIBLE);
        this.tishi.setVisibility(View.VISIBLE);

        breathAnimAndDismiss(this.tishi_bg, delayed);

        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BaseGameLayout.this.breathAnimAndDismiss(BaseGameLayout.this.tishi, delayed);
            }
        }, 100L);
    }


    protected void tipKaiPai() {
        showTishiImg(GameC.img.game_bpks);
        tipStarGame(time_tip_delay);
    }

    protected void tipXiaZhu() {
        showTishiImg(GameC.img.game_xzxyqy);
        tipStarGame(time_tip_delay);
    }

    protected void breathAnim(View targetView) {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this.mApp, R.animator.game_animator_breath);

        set.setTarget(targetView);
        set.start();
    }

    private void breathAnimAndDismiss(View targetView, long delayed) {
        breathAnim(targetView);
        breathDismissAnim(targetView, delayed);
    }

    private void breathDismissAnim(final View targetView, long delayed) {
        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                targetView.clearAnimation();
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(BaseGameLayout.this.mApp, R.animator.game_animator_breath_dismiss);

                set.setTarget(targetView);
                set.start();
            }
        }, delayed);
    }

    protected boolean checkMoney() {
        if (this.mMy_diamonds < 10) {
            final MyBaseTipDialog dialog = new MyBaseTipDialog(GameObserver.getAppObserver().getCurrentActivity());
            dialog.show();
            dialog.setMessage(R.string.gift_recharge_tip2);
            dialog.setOkButtonText(R.string.confirm);
            dialog.setOnOkButtonListener(new MyBaseTipDialog.OnOkButtonListener() {
                @Override
                public void onClick() {
                    BaseGameLayout.this.goPayListActivity();
                    dialog.dismiss();
                }
            });
            return false;
        }
        return true;
    }

    @Override
    public void setMoneyTextView(final int money, long delayed) {
        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setMoney(money);
                BaseGameLayout.this.setBetImage(money);
            }
        }, delayed);
    }

    /**
     * 发牌动画
     *
     * @param startView
     * @param targetView
     */
    protected void faPaiAnim(View startView, View targetView) {
        faPaiAnimWithPoker(startView, targetView, null);
    }

    protected void faPaiAnimWithPoker(View startView, final View targetView, final Poker poker) {
        faPaiAnimWithPoker(startView, targetView, poker, null);
    }

    protected void faPaiAnimWithPoker(View startView, final View targetView, final Poker poker, final GameAction.Void action) {
        GameAnimUtil.faPaiAnimWithPoker(startView, targetView, this.root_container, getGameType(), new GameAnimUtil.GameLiveUtilListener() {
            @Override
            public void animEnd() {
                if (action != null) {
                    action.invoke();
                }
                if (poker == null) {
                    BaseGameLayout.this.cardBackVisible(targetView);
                } else {
                    BaseGameLayout.this.cardVisible(targetView);
                    BaseGameLayout.this.getAPokerToTarget(targetView, poker.getColor(), poker
                            .getNumber());
                }
            }
        });
    }

    protected void shouPaiAnimWithPoker(View startView, View targetView) {
        GameAnimUtil.shouPaiAnimWithPoker(startView, targetView, this.root_container, getGameType());
    }

    protected abstract void cardVisible(View paramView);

    protected abstract void cardBackVisible(View paramView);

    protected void xiaZhuAnim(final View targetView) {
        ImageView fromView = this.IV_bet10;
        switch (this.currentBet) {
            case 10:
                fromView = this.IV_bet10;
                break;
            case 100:
                fromView = this.IV_bet100;
                break;
            case 1000:
                fromView = this.IV_bet1000;
                break;
            case 5000:
                fromView = this.IV_bet5000;
        }
        GameAnimUtil.shouxiaZhuAnimWithPoker(fromView, targetView, this.root_container, new GameAnimUtil.GameLiveUtilListener() {
            @Override
            public void animEnd() {
                targetView.setVisibility(VISIBLE);
            }
        });
    }

    protected abstract void setBetImage(int paramInt);

    protected abstract void openHistoryDialog(int paramInt);

    @Override
    public void sendGift(int gift_id, int giftType) {
        this.mPresenter.sendGift(gift_id, this.topic, giftType);
    }

    protected void goPayListActivity() {
        EventBus.getDefault().post(new GoPayListActivityEvent());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);

    }

    private void registerReceiver() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameResultEvent(GameResultPush gameResultPush) {

        String key = GameObserver.getAppObserver().getStringText(GameC.str.MY_DIAMONDS);
        GameObserver.getAppObserver().setObject(key, Integer.valueOf(gameResultPush.getMy_diamonds()));

        mMyWinMoney = gameResultPush.getWin_money();
        if (mWin_money_position == null) {
            mWin_money_position = new ArrayList<>();
        }
        if (getGameType() == GameC.Game.GAME_TYPE_HLNZ && gameResultPush.getWin_money_position() != null) {
            mWin_money_position.clear();
            mWin_money_position.addAll(gameResultPush.getWin_money_position());
        }
        doGameResult(gameResultPush);

        setMoneyTextView(gameResultPush.getMy_diamonds(), 13000L);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBetEvent(AllBetPush allBetPush) {
        setAllBetText(allBetPush);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKaiPaiEvent(GameKaiPaiPush kaiPaiPush) {
        if (!this.isPublish) {
            doKaiPai(kaiPaiPush, this.mGameSound);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onnextBenkerEvent(NextBenkerPush nextBenker) {
        doNextBenker(nextBenker);
    }

    /**
     * 庄家上庄的数据
     *
     * @param nextBenker
     */
    protected void doNextBenker(NextBenkerPush nextBenker) {

    }

    ;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onXiaZhuEvent(XiaZhuPush xiaZhuPush) {
        if (!this.isPublish) {
            doXiaZhu(this.mGameSound, xiaZhuPush);
        }
    }

    @Override
    public void showKaiPaiPush(GameKaiPaiPush kaiPaiPush) {
        doKaiPai(kaiPaiPush, this.mGameSound);

    }

    @Override
    public void showStartGameError() {
        LogUtils.e("showStartGameError     重新连接(主播调用)");
        if ((this.mHandler != null) &&
                (this.isPublish)) {
            this.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (BaseGameLayout.this.mPresenter != null) {
                        BaseGameLayout.this.mPresenter.startGame(BaseGameLayout.this.getGameType());
                    }
                }
            }, 3000L);
        }
    }

    @Override
    public void showCarDataError() {

    }

    @Override
    public void showendGameError() {
        LogUtils.e("showendGameError     重新连接(主播调用)");
        if ((this.mHandler != null) &&
                (this.isPublish)) {
            this.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (BaseGameLayout.this.mPresenter != null) {
                        BaseGameLayout.this.mPresenter.endGame(getGameType());
                    }
                }
            }, 3000L);
        }
    }

    @Override
    public void showXiaZhuPush(XiaZhuPush xiaZhuPush) {
        doXiaZhu(this.mGameSound, xiaZhuPush);
    }

    protected void getAPokerToTarget(View targetView, int huase, int code) {
        int r = 0;
        int g = 0;
        int b = 0;
        int dianShuRes = R.drawable.game_poker_a;
        int huaseResSm = R.drawable.game_poker_diamond;
        int huaseResBig = R.drawable.game_poker_diamond;
        switch (code) {
            case 14:
                dianShuRes = R.drawable.game_poker_a;
                break;
            case 2:
                dianShuRes = R.drawable.game_poker_2;
                break;
            case 3:
                dianShuRes = R.drawable.game_poker_3;
                break;
            case 4:
                dianShuRes = R.drawable.game_poker_4;
                break;
            case 5:
                dianShuRes = R.drawable.game_poker_5;
                break;
            case 6:
                dianShuRes = R.drawable.game_poker_6;
                break;
            case 7:
                dianShuRes = R.drawable.game_poker_7;
                break;
            case 8:
                dianShuRes = R.drawable.game_poker_8;
                break;
            case 9:
                dianShuRes = R.drawable.game_poker_9;
                break;
            case 10:
                dianShuRes = R.drawable.game_poker_10;
                break;
            case 11:
                dianShuRes = R.drawable.game_poker_j;
                break;
            case 12:
                dianShuRes = R.drawable.game_poker_q;
                break;
            case 13:
                dianShuRes = R.drawable.game_poker_k;
        }
        switch (huase) {
            case 1:
                r = 255;
                g = 0;
                b = 0;
                huaseResSm = R.drawable.game_poker_diamond;
                huaseResBig = R.drawable.game_poker_diamond;
                switch (code) {
                    case 11:
                        huaseResBig = R.drawable.game_poker_diamond_j;
                        break;
                    case 12:
                        huaseResBig = R.drawable.game_poker_diamond_q;
                        break;
                    case 13:
                        huaseResBig = R.drawable.game_poker_diamond_k;
                }
                break;
            case 2:
                r = 0;
                g = 0;
                b = 0;
                huaseResSm = R.drawable.game_poker_club;
                huaseResBig = R.drawable.game_poker_club;
                switch (code) {
                    case 11:
                        huaseResBig = R.drawable.game_poker_club_j;
                        break;
                    case 12:
                        huaseResBig = R.drawable.game_poker_club_q;
                        break;
                    case 13:
                        huaseResBig = R.drawable.game_poker_club_k;
                }
                break;
            case 3:
                r = 255;
                g = 0;
                b = 0;
                huaseResSm = R.drawable.game_poker_heart;
                huaseResBig = R.drawable.game_poker_heart;
                switch (code) {
                    case 11:
                        huaseResBig = R.drawable.game_poker_heart_j;
                        break;
                    case 12:
                        huaseResBig = R.drawable.game_poker_heart_q;
                        break;
                    case 13:
                        huaseResBig = R.drawable.game_poker_heart_k;
                }
                break;
            case 4:
                r = 0;
                g = 0;
                b = 0;
                huaseResSm = R.drawable.game_poker_spade;
                huaseResBig = R.drawable.game_poker_spade;
                switch (code) {
                    case 11:
                        huaseResBig = R.drawable.game_poker_spade_j;
                        break;
                    case 12:
                        huaseResBig = R.drawable.game_poker_spade_q;
                        break;
                    case 13:
                        huaseResBig = R.drawable.game_poker_spade_k;
                }
                break;
        }
        initAPokerCode(targetView, dianShuRes, r, g, b, huaseResSm, huaseResBig);
    }

    protected abstract void initAPokerCode(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

    @Override
    public void sendGiftResult(int my_diamonds) {
        setMoneyTextView(my_diamonds, 0L);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshMyDiamonds(RefreshGamePanelMyDiamondsEvent event) {
        String key = GameObserver.getAppObserver().getStringText(GameC.str.MY_DIAMONDS);
        setMoneyTextView(((Integer) GameObserver.getAppObserver().getObject(key, Integer.valueOf(0))).intValue(), 0L);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startLiveEvent(StartLiveResult result) {
    }

    @Override
    public void betResult(BetRespond betRespond) {
        setOneBetText(betRespond.getPosition_id(), betRespond.getMy_bet_number(), betRespond
                .getAll_bet_number());
        setMoneyTextView(betRespond.getMy_diamonds(), 0L);
    }

    @Override
    public void joinGameXiaZhu(JoinGameRespond joinGameRespond) {
        this.card_fapai.setVisibility(INVISIBLE);

        beforeNext();
        this.game_state = 3;

        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BaseGameLayout.this.mGameSound.playSound(1, 0);
                BaseGameLayout.this.game_state = 1;
            }
        }, 500L);
        djsAction(joinGameRespond.getCountdown_time());


    }

    protected abstract void djsAction(int paramInt);


    protected abstract void initPokerInfo(List<GameItemInfo> paramList);

    protected abstract void kaiPai(List<GameItemInfo> paramList);

    protected void kaiPaiNoDelayed(List<GameItemInfo> gameInfo, int tipDelayedTime) {
    }

    @Override
    public void joinGameKaiPai(final JoinGameRespond gameRespond) {
        this.whoWin = gameRespond.getWin_id();
        initPokerInfo(gameRespond.getGame_info());
        int tipDelayedTime = gameRespond.getCountdown_time() - 10;
        tipDelayedTime = tipDelayedTime < 0 ? 0 : 10;
        kaiPaiNoDelayed(gameRespond.getGame_info(), tipDelayedTime);
    }

    protected void beforeNext() {
        this.previousLeftAll = 0;
        this.previousCenterAll = 0;
        this.previousRightAll = 0;
        String game_bei_shu_0 = mContext.getString(R.string.game_bei_shu_0);
        this.bet_left_me.setText(" 0 ");
        this.bet_ping_me.setText(" 0 ");
        this.bet_right_me.setText(" 0 ");

        this.bet_left_all.setText(" " + game_bei_shu_0 + " ");
        this.bet_ping_all.setText(" " + game_bei_shu_0 + " ");
        this.bet_user_all.setText(" " + game_bei_shu_0 + " ");


        this.previousLeftAll = 0;
        this.previousCenterAll = 0;
        this.previousRightAll = 0;
    }

    @Override
    public void setOneBetText(int who, int number, int allBet) {

    }

    private String formatBetText(int num) {
        return " " + GameUtil.formatBetText(num) + " ";
    }

    private int previousLeftAll = 0;
    private int previousCenterAll = 0;
    private int previousRightAll = 0;

    @Override
    public void setAllBetText(AllBetPush allBetPush) {
        List<AllBetPush.BetInfo> betInfos = allBetPush.getBet_info();
        int one = ((AllBetPush.BetInfo) betInfos.get(0)).getAll_bet_number();
        int two = ((AllBetPush.BetInfo) betInfos.get(1)).getAll_bet_number();
        int three = ((AllBetPush.BetInfo) betInfos.get(2)).getAll_bet_number();
        if ((one > 0) && (one > this.previousLeftAll)) {
            this.previousLeftAll = one;
            String oneStr = one == 0 ? mContext.getString(R.string.game_bei_shu_0) : " " + String.valueOf(one) + " ";
            this.bet_left_all.setText(oneStr);
        }
        if ((two > 0) && (two > this.previousCenterAll)) {
            this.previousCenterAll = two;
            String twoStr = two == 0 ? mContext.getString(R.string.game_bei_shu_0) : " " + String.valueOf(two) + " ";
            this.bet_ping_all.setText(twoStr);
        }
        if ((three > 0) && (three > this.previousRightAll)) {
            this.previousRightAll = three;
            String threeStr = three == 0 ? mContext.getString(R.string.game_bei_shu_0) : " " + String.valueOf(three) + " ";
            this.bet_user_all.setText(threeStr);
        }
    }

    protected void showOtherInfoDialog(String account) {
        GameObserver.getAppObserver().showOtherInfoDialog(account);
    }

    @Override
    public void setMyBetText(int one, int two, int three) {
        this.bet_left_me.setText(" " + String.valueOf(one) + " ");
        this.bet_ping_me.setText(" " + String.valueOf(two) + " ");
        this.bet_right_me.setText(" " + String.valueOf(three) + " ");
    }
//    String oneStr = one == 0 ? mContext.getString(R.string.game_bei_shu_0) : " " + String.valueOf(one) + " ";
//    String twoStr = two == 0 ? mContext.getString(R.string.game_bei_shu_0) : " " + String.valueOf(two) + " ";
//    String threeStr = three == 0 ? mContext.getString(R.string.game_bei_shu_0) : " " + String.valueOf(three) + " ";
}

