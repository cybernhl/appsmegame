package weking.lib.game.base;


import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

import app.io.weking.anim.bean.Gift;
import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.dialog.GameBetDialog;
import weking.lib.game.dialog.GameCowboyBankerListDialog;
import weking.lib.game.dialog.GameCowboyBenderInfoDialog;
import weking.lib.game.dialog.GameCowboyHistoryDialog;
import weking.lib.game.dialog.GameHistoryDialog;
import weking.lib.game.dialog.GameResultDialog;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.FastClickUtil;
import weking.lib.utils.ToastUtil;


public abstract class BaseGameDialogLayout extends BaseGameLayout {
    private GameResultDialog mGameResultDialog;
    private GameBetDialog mBetBialog;
    //  开牌记录
    private GameHistoryDialog mHistorydialog;
    private GameCowboyHistoryDialog mCowboyHistorydialog;
    //  庄家列表
    protected GameCowboyBankerListDialog mBankerListdialog;
    //  庄家流水
    private GameCowboyBenderInfoDialog mBenderInfodialog;
    // 庄家的钱
    protected int mBanker_diamond;
    // 庄家的account
    public static String mBanker_account;

    public BaseGameDialogLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void showWinDialog(int winMoney, List<Gift> gifts, boolean isShouAnim) {

        if (this.mGameResultDialog == null) {
            this.mGameResultDialog = new GameResultDialog(GameObserver.getAppObserver().getCurrentActivity(), this.isPublish);
            this.mGameResultDialog.setPlayActionListener(new GameResultDialog.GamePlayActionListener() {
                @Override
                public void sendGift(int giftId, int giftType) {
                    BaseGameDialogLayout.this.sendGift(giftId, giftType);
                }
            });
        }
        try {
            this.mGameResultDialog.show();
            this.mGameResultDialog.setWinMoney(winMoney);
            this.mGameResultDialog.setGifView(gifts);
            if (isShouAnim) {
                this.mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseGameDialogLayout.this.mGameResultDialog.winDialogAnim();
                    }
                }, 500L);


                this.mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseGameDialogLayout.this.mGameResultDialog.winDialogAnim();
                    }
                }, 600L);


                this.mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseGameDialogLayout.this.mGameResultDialog.winDialogAnim();
                    }
                }, 700L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void openHistoryDialog(int gameType) {
        if (GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID) == 0) {
            ToastUtil.show(R.string.null_internet);
            return;
        }
        if (gameType == GameC.Game.GAME_TYPE_HLNZ) {
            if (mCowboyHistorydialog == null) {
                mCowboyHistorydialog = new GameCowboyHistoryDialog(GameObserver.getAppObserver().getCurrentActivity(), gameType);
            }
            mCowboyHistorydialog.show();
            mCowboyHistorydialog.getHistory();

        } else {
            if (mHistorydialog == null) {
                mHistorydialog = new GameHistoryDialog(GameObserver.getAppObserver().getCurrentActivity(), gameType);
            }
            mHistorydialog.show();
            mHistorydialog.getHistory();
        }

    }

    protected void openBankerListDialog() {
        if (GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID) == 0) {
            ToastUtil.show(R.string.null_internet);
            return;
        }
        if (mBankerListdialog == null) {
            mBankerListdialog = new  GameCowboyBankerListDialog(GameObserver.getAppObserver().getCurrentActivity());
        }
        mBankerListdialog.getHistory();
        mBankerListdialog.show();
    }

    protected void openBenderInfoDialog() {
        if (GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID) == 0) {
            ToastUtil.show(R.string.null_internet);
            return;
        }
        if (mBenderInfodialog == null) {
            mBenderInfodialog = new GameCowboyBenderInfoDialog(GameObserver.getAppObserver().getCurrentActivity());
        }
        mBenderInfodialog.show();
        mBenderInfodialog.getHistory();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mGameResultDialog = null;
    }

    @Override
    protected void beforeNext() {
        super.beforeNext();
        if (this.mGameResultDialog != null) {
            this.mGameResultDialog.dismiss();
        }
    }


    protected void openBetDialog() {
        if (FastClickUtil.isFastClick(600)) {
            return;
        }
        if (this.mBetBialog == null) {
            this.mBetBialog = new GameBetDialog(GameObserver.getAppObserver().getCurrentActivity());
        }
        this.mBetBialog.show();
        this.mBetBialog.getHistory();
    }

    @Override
    public void startGameDollData(List<List<XiaZhuPush.GameBaen>> game_info) {
    }

    @Override
    public void startGamestar_warsData(List<XiaZhuPush.GameBaen> star_wars) {
    }

    @Override
    public void showFireResult(boolean paramBoolean, int paramInt) {

    }

    @Override
    public void showFireError() {

    }
}


