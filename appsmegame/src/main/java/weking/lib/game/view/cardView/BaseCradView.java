package weking.lib.game.view.cardView;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.base.BaseGameDialogLayout;
import weking.lib.game.bean.BetRespond;
import weking.lib.game.bean.GameItemInfo;
import weking.lib.game.bean.GameKaiPaiPush;
import weking.lib.game.bean.JoinGameRespond;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.bean.push.GameResultPush;
import weking.lib.game.manager.GameSound;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.BottomBarUtil;
import weking.lib.game.utils.FastClickUtil;
import weking.lib.game.utils.GameUIUtils;
import weking.lib.game.utils.GameUtil;
import weking.lib.game.view.goodview.GoodView;
import weking.lib.game.view.popWin.GameCradMenuPoPWindow;
import weking.lib.utils.BitmapUtil;
import weking.lib.utils.LogUtils;

import static weking.lib.game.utils.GameUtil.getMoney;

/**
 * 创建时间 2017/8/8.
 * 创建人 frs
 * 功能描述
 */
public abstract class BaseCradView extends BaseGameDialogLayout {
    protected GameCradMenuPoPWindow menuPopupWindow;  // 菜单的popopu
    protected int mWindowWidth;
    protected ProgressBar cbProgress;
    protected float mThreeMargin;
    protected float mTreePokerWidth;  // 桌面的width
    protected ImageView iv_poker_info_icon_left, iv_poker_info_icon_ping, iv_poker_info_icon_right; // 桌面背景
    private ImageView iv_menu;  // 菜单


    public BaseCradView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void doGameResult(final GameResultPush gameResultPush) {
        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                winOrLoseTip(gameResultPush.getWin_money(), gameResultPush
                        .getGift_info(), mGameSound, 12000L);
            }
        }, time_win_delay);
    }

    @Override
    public void init() {
        super.init();
        mWindowWidth = GameUtil.getWindowWidth(GameObserver.getAppObserver().getApp());
        mThreeMargin = GameUtil.getDimension(R.dimen.game_djs_three_poker_margin);
        mTreePokerWidth = mWindowWidth - (mThreeMargin * 4);

        iv_menu = (ImageView) this.findViewById(R.id.iv_menu);
        cbProgress = (ProgressBar) this.findViewById(R.id.my_progress);
        IV_bet10 = (ImageView) findViewById(R.id.bet10);
        IV_bet100 = (ImageView) findViewById(R.id.bet100);
        IV_bet1000 = (ImageView) findViewById(R.id.bet1000);
        IV_bet5000 = (ImageView) findViewById(R.id.bet5000);
        tv_money = (TextView) findViewById(R.id.tv_money);
        chongzhi = findViewById(R.id.chongzhi);
        root_container = (ViewGroup) findViewById(R.id.root_container);

        card_fapai = (ImageView) findViewById(R.id.card_fapai);

        tishi = (ImageView) findViewById(R.id.tishi);
        tishi_bg = (ImageView) findViewById(R.id.tishi_bg);//null

        rl_bet_left = findViewById(R.id.rl_bet_left);
        rl_bet_ping = findViewById(R.id.rl_bet_ping);
        rl_bet_right = findViewById(R.id.rl_bet_right);

        tv_bei_shu_left = (TextView) rl_bet_left.findViewById(R.id.textView_bei_shu);
        tv_bei_shu_ping = (TextView) rl_bet_ping.findViewById(R.id.textView_bei_shu);
        tv_bei_shu_right = (TextView) rl_bet_right.findViewById(R.id.textView_bei_shu);

        // 应用字体
        tv_bei_shu_left.setTypeface(mTypeFace);
        tv_bei_shu_ping.setTypeface(mTypeFace);
        tv_bei_shu_right.setTypeface(mTypeFace);

        bet_left_all = (TextView) rl_bet_left.findViewById(R.id.bet_all);
        bet_ping_all = (TextView) rl_bet_ping.findViewById(R.id.bet_all);
        bet_user_all = (TextView) rl_bet_right.findViewById(R.id.bet_all);

        // 应用字体
        bet_left_all.setTypeface(mTypeFace);
        bet_ping_all.setTypeface(mTypeFace);
        bet_user_all.setTypeface(mTypeFace);

        bet_left_me = (TextView) rl_bet_left.findViewById(R.id.bet_me);
        bet_ping_me = (TextView) rl_bet_ping.findViewById(R.id.bet_me);
        bet_right_me = (TextView) rl_bet_right.findViewById(R.id.bet_me);
        // 应用字体
        bet_left_me.setTypeface(mTypeFace);
        bet_ping_me.setTypeface(mTypeFace);
        bet_right_me.setTypeface(mTypeFace);

        iv_poker_info_icon_left = (ImageView) rl_bet_left.findViewById(R.id.iv_poker_info_icon);
        iv_poker_info_icon_ping = (ImageView) rl_bet_ping.findViewById(R.id.iv_poker_info_icon);
        iv_poker_info_icon_right = (ImageView) rl_bet_right.findViewById(R.id.iv_poker_info_icon);

        iv_menu.setOnClickListener(this);
        if (!isPublish) {
            iv_menu.setImageResource(R.drawable.selector_game_card_buttom_menu_cards);
        }

        view_tip = this.findViewById(R.id.view_tip);
        rl_bet_left.setOnClickListener(this);
        rl_bet_ping.setOnClickListener(this);
        rl_bet_right.setOnClickListener(this);

        IV_bet10.setOnClickListener(this);
        IV_bet100.setOnClickListener(this);
        IV_bet1000.setOnClickListener(this);
        IV_bet5000.setOnClickListener(this);
        chongzhi.setOnClickListener(this);
        findViewById(R.id.crad_toolbar).setOnClickListener(this);
        mMy_diamonds = getMoney();
        GameUIUtils.initnotityCradUI(mMy_diamonds, currentBet, IV_bet10, IV_bet100, IV_bet1000, IV_bet5000);
    }


    /**
     * 下注时间倒计时任务
     */
    Runnable timeDJSTask = new Runnable() {
        @Override
        public void run() {


            cbProgress.setProgress(/*cbProgress.getMax() -*/ timeDJS);
            timeDJS--;
            if (timeDJS == 1) {
                // 主播进行命令开牌发送
                endGame();
            }
            if (timeDJS < 0) {
                game_state = GAME_STATE_KAI_PAI;
                mHandler.removeCallbacks(timeDJSTask);
                return;
            }
            if (mHandler != null) {
                mHandler.postDelayed(timeDJSTask, 1000 / 5);
            }


        }
    };


    // 开始倒计时下注时间
    @Override
    protected void djsAction(int timeInt) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipXiaZhu();
            }
        }, 500);
        timeDJS = timeInt * 5;
        LogUtils.d("  timeDJS  " + timeDJS);
        cbProgress.setMax(timeDJS);

        mHandler.post(timeDJSTask);
    }

    @Override
    public void doKaiPai(GameKaiPaiPush gamePush, GameSound gameSound) {
        super.doKaiPai(gamePush, gameSound);

        try {
            this.whoWin = gamePush.getWin_id();

            this.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    game_state = 2;
                    tipKaiPai();
                }
            }, 1000L);


            final List<GameItemInfo> gameInfo = gamePush.getGame_info();
            if (gameInfo.size() != 3) {
                GameObserver.getAppObserver().showLog("BaseGameLayout", "服务器返回的牌错误；gameInfo数不是3");
            } else {
                initPokerInfo(gameInfo);

                this.mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        kaiPai(gameInfo);
                    }
                }, 4000L);
            }
        } catch (Exception e) {
            GameObserver.getAppObserver().showLog("BaseGameLayout", e.getMessage());
            e.printStackTrace();
        } finally {
            doStartGameAction(gamePush.getCountdown_time());
        }
    }

    /**
     * 设置我的钻石
     *
     * @param money 我的钻石
     */
    @Override
    protected void setBetImage(int money) {
        currentBet = BottomBarUtil.setBetImage(IV_bet10, IV_bet100, IV_bet1000, IV_bet5000, money, currentBet);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.chongzhi) {
            goPayListActivity();

        } else if (i == R.id.rl_bet_left) {//
            if (!checkMoney()) {
                return;
            }
            if (game_state == GAME_STATE_XIA_ZHU) {
//                    xiaZhuAnim(rl_bet_left);

                if (mPresenter != null) {
                    int live_id = GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID);
                    mPresenter.bet(live_id, BET_LEFT, currentBet);
                }
            }

        } else if (i == R.id.rl_bet_ping) {
            if (!checkMoney()) {
                return;
            }
            if (game_state == GAME_STATE_XIA_ZHU) {
//                    xiaZhuAnim(rl_bet_ping);

                if (mPresenter != null) {
                    int live_id = GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID);

//                        mPresenter.bet(C.IN_ROOM_LIVE_ID, BET_CENTER, currentBet);
                    mPresenter.bet(live_id, BET_CENTER, currentBet);
                }
            }

        } else if (i == R.id.rl_bet_right) {
            if (!checkMoney()) {
                return;
            }
            if (game_state == GAME_STATE_XIA_ZHU) {
//                    xiaZhuAnim(rl_bet_right);

                if (mPresenter != null) {
                    int live_id = GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID);

                    mPresenter.bet(live_id, BET_RIGHT, currentBet);
                }
            }

        } else if (i == R.id.bet10) {
            setBetUI(IV_bet10, BET_10);
        } else if (i == R.id.bet100) {
            setBetUI(IV_bet100, BET_100);
        } else if (i == R.id.bet1000) {
            setBetUI(IV_bet1000, BET_1000);
        } else if (i == R.id.bet5000) {
//            resetBetBG();
//            currentBet = BET_5000;
//            IV_bet5000.setImageResource(R.drawable.game_bet_5k);
            setBetUI(IV_bet5000, BET_5000);
        } else if (i == R.id.iv_menu) { //竞猜榜
            if (isPublish) {
                showMenuWindow(iv_menu);
            } else {
                openHistoryDialog(getGameType());
            }
        }
    }

    protected void setBetUI(ImageView iv_bet, int bet) {
        currentBet = bet;
        if (bet > mMy_diamonds) {
            GameUIUtils.showCheckMoney();
            return;
        }
        mMy_diamonds = getMoney();
        GameUIUtils.notityCradUI(mMy_diamonds, currentBet, IV_bet10, IV_bet100, IV_bet1000, IV_bet5000);

//        resetBetBG();
//        currentBet = bet;
//        switch (bet) {
//            case BET_10:
//                iv_bet.setImageResource(R.drawable.game_bet_10);
//                break;
//            case BET_100:
//                iv_bet.setImageResource(R.drawable.game_bet_100);
//                break;
//            case BET_1000:
//                iv_bet.setImageResource(R.drawable.game_bet_1k);
//                break;
//            case BET_5000:
//                iv_bet.setImageResource(R.drawable.game_bet_5k);
//                break;
//        }
    }

    protected void showMenuWindow(ImageView iv_menu) {
        if (FastClickUtil.isFastClick(500)) {
            return;
        }
        if (menuPopupWindow == null) {
            // 创建一个PopuWidow对象
            menuPopupWindow = new GameCradMenuPoPWindow(GameObserver.getAppObserver().getCurrentActivity());
        }
        menuPopupWindow.setGameCardMenuListener(new GameCradMenuPoPWindow.GameCardMenuListener() {
            @Override
            public void OnClickWinningRecord() {
                openBetDialog();
            }

            @Override
            public void OnClickCardFapai() {
                openHistoryDialog(getGameType());
            }
        });
        menuPopupWindow.showPopupWindow(iv_menu);
    }


//    /**
//     * 重置筹码背景
//     */
//    private void resetBetBG() {
//
//        switch (currentBet) {
//            case BET_10:
//                IV_bet10.setImageResource(R.drawable.game_bet_10_nor);
//                break;
//            case BET_100:
//                IV_bet100.setImageResource(R.drawable.game_bet_100_nor);
//                break;
//            case BET_1000:
//                IV_bet1000.setImageResource(R.drawable.game_bet_1k_nor);
//                break;
//            case BET_5000:
//                IV_bet5000.setImageResource(R.drawable.game_bet_5k_nor);
//                break;
//
//        }
//    }

    /**
     * 盖牌
     *
     * @param targetView
     */
    @Override
    protected void cardBackVisible(View targetView) {
        targetView.setVisibility(VISIBLE);
//        ((ImageView) targetView.findViewById(R.id.iv_bg)).setImageResource(GameObserver.getAppObserver().getImagRes(GameC.img.game_poker_back_goldflower));
        int pokerBgRes = GameUIUtils.getPokerRes(getGameType());
        if (pokerBgRes != 0) {
            ((ImageView) targetView.findViewById(R.id.iv_bg)).setImageResource(pokerBgRes);
        }
        targetView.findViewById(R.id.iv_bg).setVisibility(VISIBLE);
    }

    /**
     * 翻牌
     *
     * @param targetView
     */
    @Override
    protected void cardVisible(View targetView) {
        targetView.setVisibility(VISIBLE);
        targetView.findViewById(R.id.iv_bg).setVisibility(GONE);
    }

    @Override
    protected void initAPokerCode(View card, int codeRes, int r, int g, int b,
                                  int huaseResSm, int huaseBig) {
        ImageView iv_pokercode = (ImageView) card.findViewById(R.id.iv_pokercode);
        ImageView iv_pokerSizesl = (ImageView) card.findViewById(R.id.iv_pokerSizesl);
        ImageView iv_pokerSizebig = (ImageView) card.findViewById(R.id.iv_pokerSizesm);
        iv_pokerSizesl.setImageResource(huaseResSm);
        iv_pokerSizebig.setImageResource(huaseBig);
        iv_pokercode.setImageBitmap(BitmapUtil.changeColor(mApp, codeRes, r, g, b));

    }

    @Override
    protected void showFapaiCard() {
        if (isPublish) {
            // 发牌的背景
            int pokerRes = GameUIUtils.getPokerRes(getGameType());
            if (pokerRes != 0) {
                card_fapai.setImageResource(pokerRes);
            }

        }
    }

    @Override
    public void betResult(BetRespond betRespond) {
        super.betResult(betRespond);
        switch (betRespond.getPosition_id()) {
            case BET_LEFT:
                bet_left_me.setText(" " + betRespond.getMy_bet_number() + " ");
                break;
            case BET_CENTER:
                bet_ping_me.setText(" " + betRespond.getMy_bet_number() + " ");
                break;
            case BET_RIGHT:
                bet_right_me.setText(" " + betRespond.getMy_bet_number() + " ");
            default:
                break;
        }
        GoodView goodView = new GoodView(getContext(), R.color.game_app_theme_color);
        goodView.setText("+" + currentBet + " ", mTypeFace);
        switch (betRespond.getPosition_id()) {
            case BET_LEFT:
                goodView.show(tv_bei_shu_left);
                break;
            case BET_CENTER:
                goodView.show(tv_bei_shu_ping);
                break;
            case BET_RIGHT:
                goodView.show(tv_bei_shu_right);
                break;
            default:
                break;
        }
    }

    @Override
    public void joinGameKaiPai(final JoinGameRespond gameRespond) {
        super.joinGameKaiPai(gameRespond);
        int tipDelayedTime = gameRespond.getCountdown_time() - 10;
        tipDelayedTime = tipDelayedTime < 0 ? 0 : 10;
        final int starDelayedTime = tipDelayedTime > 12 ? 12 : tipDelayedTime;
        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                winOrLoseTip(gameRespond.getWin_money(), gameRespond
                        .getGift_info(), mGameSound, starDelayedTime * 1000);
            }
        }, tipDelayedTime * 1000);
    }

    /**
     * 游戏开始请稍后
     */
    @Override
    protected void showTishiImg(String tishiType) {
        int imgRes = 0;
        if (TextUtils.equals(tishiType, GameC.img.game_yxjjks)) {
            imgRes = R.drawable.game_yxjjks;
        } else if (TextUtils.equals(tishiType, GameC.img.game_jyxcbz)) {
            imgRes = R.drawable.game_jyxcbz;
        } else if (TextUtils.equals(tishiType, GameC.img.game_bpks)) {
            imgRes = R.drawable.game_bpks;
        } else if (TextUtils.equals(tishiType, GameC.img.game_xzxyqy)) {
            imgRes = R.drawable.game_xzxyqy;
        }


        if (imgRes != 0) {
            tishi.setImageResource(imgRes);
        }

    }

    protected void faPaiAnimAll(int time) {

        if (isPublish) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    card_fapai.setVisibility(INVISIBLE);
                }
            }, time);
        } else {
            card_fapai.setVisibility(VISIBLE);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    card_fapai.setVisibility(INVISIBLE);
                }
            }, time + 500);

        }
    }

    @Override
    public void doXiaZhu(final GameSound gameSound, XiaZhuPush xiaZhuPush) {
        beforeNext();

        this.game_state = 3;


        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameSound.playSound(1, 0);
                game_state = 1;
                tipXiaZhu();
            }
        }, 2000L);
    }

    protected void tipTextAnim(View bg, final View text) {
        bg.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);

        breathAnim(bg);

        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                breathAnim(text);
            }
        }, 100L);
    }
}
