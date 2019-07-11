package weking.lib.game.view.car;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.bean.AllBetPush;
import weking.lib.game.bean.BetRespond;
import weking.lib.game.bean.GameKaiPaiPush;
import weking.lib.game.bean.JoinGameRespond;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.bean.push.GameResultPush;
import weking.lib.game.dialog.GameBetDialog;
import weking.lib.game.dialog.GameCowboyHistoryDialog;
import weking.lib.game.dialog.GameHistoryDialog;
import weking.lib.game.dialog.MyBaseTipDialog;
import weking.lib.game.event.CarEndLiveEndEvent;
import weking.lib.game.event.GameStartEvent;
import weking.lib.game.event.GoPayListActivityEvent;
import weking.lib.game.event.LiveNoEndEvent;
import weking.lib.game.manager.GameSound;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.presenter.IGameFactory;
import weking.lib.game.utils.BottomBarUtil;
import weking.lib.game.utils.FastClickUtil;
import weking.lib.game.utils.GameAction;
import weking.lib.game.utils.GameAnimUtil;
import weking.lib.game.utils.GameUIUtils;
import weking.lib.game.view.popWin.GameCradMenuPoPWindow;
import weking.lib.utils.LogUtils;
import weking.lib.utils.ToastUtil;

import static weking.lib.game.base.BaseGameLayout.BET_CENTER;
import static weking.lib.game.base.BaseGameLayout.BET_LEFT;
import static weking.lib.game.base.BaseGameLayout.BET_RIGHT;
import static weking.lib.game.utils.GameUtil.getMoney;

/**
 * Created by Administrator on 2017/12/27.
 */

public abstract class BaseCarLayout extends IGameFactory{


    protected static final int GAME_STATE_XIA_ZHU = 1;
    protected static final int GAME_STATE_BEGIN = 0;
    protected static final int GAME_STATE_SAI_CHE = 2;
    protected static final int GAME_STATE_END = 3;
    protected static final int GAME_STATE_SWITCH = 4;


    protected int game_state = GAME_STATE_BEGIN;

    public static final int time_win_delay = 2000;

    protected boolean liveIsEnd = false;

    protected boolean isPublish = false;
    protected Context context ;

    private GameBetDialog mBetBialog;
    //  开牌记录
    private GameHistoryDialog mHistorydialog;

    private GameCowboyHistoryDialog mCowboyHistorydialog;

    protected GameCradMenuPoPWindow menuPopupWindow;  // 菜单的popopu

    // 自定义下注
    protected View view_left_bet,view_center_bet, view_right_bet;
    // 下注背景
    protected ImageView iv_bet_left_bg,iv_bet_center_bg, iv_bet_right_bg;
    // 下注区域
    protected CrazyCarBetView cbv_bet_left, cbv_bet_center, cbv_bet_right;
    // 下注金额
    protected TextView tv_bet_all_left,tv_bet_all_center,tv_bet_all_right
            ,tv_bet_me_left,tv_bet_me_center,tv_bet_me_right;

    protected ImageView iv_menu;
    // 选择下注金额
    protected ImageView bet5000,bet1000,bet100,bet10;
//    // 余额
//    protected TextView tv_money;
    // 充值
    protected ImageView chongzhi;
    // 结束界面
    protected View game_car_end_view;

    protected ProgressBar pb_black_car_end,pb_red_car_end,pb_yellow_car_end;

    protected TextView tv_count_down;

    protected ViewGroup root_container;

    protected ImageView iv_sai_che;

    protected ImageView iv_end;

    protected ImageView money;
    protected ImageView iv_win_left,iv_win_center,iv_win_right;

    protected TextView tv_end;

    protected RelativeLayout rl_end_begin;

    private int currentBet = 10;

    protected int timeDJS = 30;

    private int previousLeftAll = 0;
    private int previousCenterAll = 0;
    private int previousRightAll = 0;

    protected int win_id = 0;

    protected int switch_type;

    // 输赢的钱
    protected ArrayList<Integer> mWin_money_position;
    //自己赢得钱
    protected int mMyWinMoney;

    public BaseCarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void init(){
        view_left_bet = findViewById(R.id.view_left_bet);
        view_center_bet = findViewById(R.id.view_center_bet);
        view_right_bet = findViewById(R.id.view_right_bet);

        tv_bet_all_left = view_left_bet.findViewById(R.id.tv_bet_all);
        tv_bet_me_left = view_left_bet.findViewById(R.id.tv_bet_me);
        tv_bet_all_center = view_center_bet.findViewById(R.id.tv_bet_all);
        tv_bet_me_center = view_center_bet.findViewById(R.id.tv_bet_me);
        tv_bet_all_right = view_right_bet.findViewById(R.id.tv_bet_all);
        tv_bet_me_right = view_right_bet.findViewById(R.id.tv_bet_me);

        iv_bet_left_bg = (ImageView)view_left_bet.findViewById(R.id.iv_bet_bg);
        iv_bet_center_bg = (ImageView)view_center_bet.findViewById(R.id.iv_bet_bg);
        iv_bet_right_bg = (ImageView)view_right_bet.findViewById(R.id.iv_bet_bg);

        cbv_bet_left = (CrazyCarBetView)view_left_bet.findViewById(R.id.car_bet_left);
        cbv_bet_center = (CrazyCarBetView)view_center_bet.findViewById(R.id.car_bet_left);
        cbv_bet_right = (CrazyCarBetView)view_right_bet.findViewById(R.id.car_bet_left);

        bet5000 =(ImageView) findViewById(R.id.bet5000);
        bet1000 = (ImageView)findViewById(R.id.bet1000);
        bet100 = (ImageView)findViewById(R.id.bet100);
        bet10 = (ImageView)findViewById(R.id.bet10);

        tv_money = (TextView) findViewById(R.id.tv_money);
        chongzhi =(ImageView) findViewById(R.id.chongzhi);
        iv_menu = (ImageView)findViewById(R.id.iv_menu);

        iv_end = (ImageView)findViewById(R.id.iv_end);

        money = (ImageView)findViewById(R.id.money);

        iv_win_left = (ImageView)view_left_bet.findViewById(R.id.iv_win);
        iv_win_center = (ImageView)view_center_bet.findViewById(R.id.iv_win);
        iv_win_right = (ImageView)view_right_bet.findViewById(R.id.iv_win);

        iv_sai_che = (ImageView)findViewById(R.id.iv_sai_che);

        game_car_end_view = findViewById(R.id.game_car_end_view);

        pb_black_car_end = (ProgressBar) findViewById(R.id.pb_black_car_end);
        pb_red_car_end = (ProgressBar) findViewById(R.id.pb_red_car_end);
        pb_yellow_car_end = (ProgressBar) findViewById(R.id.pb_yellow_car_end);

        tv_count_down = (TextView) findViewById(R.id.tv_count_down);
        tv_end = (TextView) findViewById(R.id.tv_end);
        root_container = findViewById(R.id.root_container);

        rl_end_begin = (RelativeLayout) findViewById(R.id.rl_end_begin);

        bet5000.setOnClickListener(this);
        bet1000.setOnClickListener(this);
        bet100.setOnClickListener(this);
        bet10.setOnClickListener(this);

        tv_money.setOnClickListener(this);
        chongzhi.setOnClickListener(this);
        iv_menu.setOnClickListener(this);

        view_left_bet.setOnClickListener(this);
        view_center_bet.setOnClickListener(this);
        view_right_bet.setOnClickListener(this);

        mMy_diamonds = getMoney();
        tv_money.setText(mMy_diamonds+"");
        initnotityCradUI(mMy_diamonds, currentBet, bet10, bet100, bet1000, bet5000);


        tv_bet_all_left.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Aku&Kamu.otf"));
        tv_bet_me_left.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Aku&Kamu.otf"));
        tv_bet_all_center.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Aku&Kamu.otf"));
        tv_bet_me_center.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Aku&Kamu.otf"));
        tv_bet_all_right.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Aku&Kamu.otf"));
        tv_bet_me_right.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Aku&Kamu.otf"));

    }

    public static void initnotityCradUI(int money, int currentBet, ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000, ImageView iv_bet5000) {

        if (money < GameAnimUtil.BET_10) {
            iv_bet10.setImageResource(R.drawable.icon_chip_na);
            iv_bet100.setImageResource(R.drawable.icon_chip_na);
            iv_bet1000.setImageResource(R.drawable.icon_chip_na);
            iv_bet5000.setImageResource(R.drawable.icon_chip_na);
        } else if (money < GameAnimUtil.BET_50) {
            iv_bet10.setImageResource(R.drawable.icon_chip_10);
            iv_bet100.setImageResource(R.drawable.icon_chip_na);
            iv_bet1000.setImageResource(R.drawable.icon_chip_na);
            iv_bet5000.setImageResource(R.drawable.icon_chip_na);
        } else if (money < GameAnimUtil.BET_100) {
            iv_bet10.setImageResource(R.drawable.icon_chip_10);
            iv_bet100.setImageResource(R.drawable.icon_chip_50_nor);
            iv_bet1000.setImageResource(R.drawable.icon_chip_na);
            iv_bet5000.setImageResource(R.drawable.icon_chip_na);
            if (currentBet == GameAnimUtil.BET_50) {
                iv_bet10.setImageResource(R.drawable.icon_chip_10_nor);
                iv_bet100.setImageResource(R.drawable.icon_chip_50);
            } else {
                iv_bet10.setImageResource(R.drawable.icon_chip_10);
            }
        }  else if (money < GameAnimUtil.BET_500) {
            iv_bet10.setImageResource(R.drawable.icon_chip_10_nor);
            iv_bet100.setImageResource(R.drawable.icon_chip_50_nor);
            iv_bet1000.setImageResource(R.drawable.icon_chip_100_nor);
            iv_bet5000.setImageResource(R.drawable.icon_chip_na);
            if (currentBet == GameAnimUtil.BET_100) {
                iv_bet10.setImageResource(R.drawable.icon_chip_10_nor);
                iv_bet100.setImageResource(R.drawable.icon_chip_50_nor);
                iv_bet1000.setImageResource(R.drawable.icon_chip_100);
            } else if (currentBet == GameAnimUtil.BET_50) {
                iv_bet10.setImageResource(R.drawable.icon_chip_10_nor);
                iv_bet100.setImageResource(R.drawable.icon_chip_50);
            } else {
                iv_bet10.setImageResource(R.drawable.icon_chip_10);
            }
        } else {
            iv_bet10.setImageResource(R.drawable.icon_chip_10_nor);
            iv_bet100.setImageResource(R.drawable.icon_chip_50_nor);
            iv_bet1000.setImageResource(R.drawable.icon_chip_100_nor);
            iv_bet5000.setImageResource(R.drawable.icon_chip_500_nor);
            if (currentBet == GameAnimUtil.BET_10) {
                iv_bet10.setImageResource(R.drawable.icon_chip_10);
            } else if (currentBet == GameAnimUtil.BET_50) {
                iv_bet100.setImageResource(R.drawable.icon_chip_50);
            } else if (currentBet == GameAnimUtil.BET_100) {
                iv_bet1000.setImageResource(R.drawable.icon_chip_100);
            } else if (currentBet == GameAnimUtil.BET_500) {
                iv_bet5000.setImageResource(R.drawable.icon_chip_500);
            } else {
                iv_bet10.setImageResource(R.drawable.icon_chip_10);
            }
        }
    }

    public static void notityCradUI(int money, int currentBet, ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000, ImageView iv_bet5000) {
        initnotityCradUI(money, currentBet, iv_bet10, iv_bet100, iv_bet1000, iv_bet5000);
        if (currentBet == GameAnimUtil.BET_500) {
            startScaleAnim(iv_bet5000);
            iv_bet5000.setImageResource(R.drawable.icon_chip_500);
        } else if (currentBet == GameAnimUtil.BET_100) {
            iv_bet1000.setImageResource(R.drawable.icon_chip_100);
            startScaleAnim(iv_bet1000);
        } else if (currentBet == GameAnimUtil.BET_50) {
            iv_bet100.setImageResource(R.drawable.icon_chip_50);
            startScaleAnim(iv_bet100);
        } else {
            iv_bet10.setImageResource(R.drawable.icon_chip_10);
            startScaleAnim(iv_bet10);
        }
    }

    //缩放动画
    private static void startScaleAnim(ImageView iv_bet) {
        final ScaleAnimation animation = new ScaleAnimation(0.0f, 1.3f, 0.0f, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);
        iv_bet.startAnimation(animation);
    }

    //加入游戏下注阶段
    @Override
    public void joinGameXiaZhu(JoinGameRespond joinGameRespond) {
        super.joinGameXiaZhu(joinGameRespond);
        iv_sai_che.setVisibility(GONE);
        beforeNext();
        game_state = GAME_STATE_XIA_ZHU;
        LogUtils.d("赛车  joinGameRespond  " + joinGameRespond.getCountdown_time());
        djsAction(joinGameRespond.getCountdown_time());
    }

    // 加入游戏赛车阶段
    @Override
    public void joinGameKaiPai(JoinGameRespond gameRespond) {
        super.joinGameKaiPai(gameRespond);
        game_state = GAME_STATE_SAI_CHE;
        iv_sai_che.setVisibility(VISIBLE);
    }

    // 开始倒计时下注时间
    protected void djsAction(final int timeInt) {
        timeDJS = timeInt;
        LogUtils.d("赛车  timeDJS  " + timeDJS);
        mHandler.post(timeDJSTask);
    }

    /**
     * 下注时间倒计时任务
     */
    Runnable timeDJSTask = new Runnable() {
        @Override
        public void run() {
            timeDJS--;
            LogUtils.d("赛车  timeDJS  " + timeDJS);
            if (timeDJS == 0) {
                // 主播进行命令开牌发送
                getCarData();
            }
            if (timeDJS < 0) {
//                game_state = G;
                mHandler.removeCallbacks(timeDJSTask);
//                mGowBoyFrament.heidTime();
                return;
            }
            if (mHandler != null) {
                mHandler.postDelayed(timeDJSTask, 1000);
                tv_count_down.setText(timeDJS+"");
            }


        }
    };

    private void getCarData() {
        if(isPublish){
            mPresenter.getCarResult();
        }
    }

    protected void beforeNext() {
        this.previousLeftAll = 0;
        this.previousCenterAll = 0;
        this.previousRightAll = 0;
        String game_bei_shu_0 = "0";
        tv_bet_me_left.setText("");
        tv_bet_me_center.setText("");
        tv_bet_me_right.setText("");

        tv_bet_all_left.setText(" " + game_bei_shu_0 + " ");
        tv_bet_all_center.setText(" " + game_bei_shu_0 + " ");
        tv_bet_all_right.setText(" " + game_bei_shu_0 + " ");

        this.previousLeftAll = 0;
        this.previousCenterAll = 0;
        this.previousRightAll = 0;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.chongzhi) {
            EventBus.getDefault().post(new GoPayListActivityEvent());
        }else if(i == R.id.view_left_bet){
            if (!checkMoney()) {
                return;
            }

            if (game_state == GAME_STATE_XIA_ZHU) {
                if (mPresenter != null) {
                    int live_id = GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID);
                    mPresenter.bet(live_id, BET_LEFT, currentBet);
                }
            }
        }else if (i == R.id.view_center_bet) {
            if (!checkMoney()) {
                return;
            }

            if (game_state == GAME_STATE_XIA_ZHU) {
                if (mPresenter != null) {
                    int live_id = GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID);
                    mPresenter.bet(live_id, BET_CENTER, currentBet);
                }
            }

        } else if (i == R.id.view_right_bet) {
            if (!checkMoney()) {
                return;
            }
            if (game_state == GAME_STATE_XIA_ZHU) {
                if (mPresenter != null) {
                    int live_id = GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID);
                    mPresenter.bet(live_id, BET_RIGHT, currentBet);
                }
            }
        }else if (i == R.id.bet10) {
            setBetUI(bet10,GameAnimUtil.BET_10);
        } else if (i == R.id.bet100) {
            setBetUI(bet100, GameAnimUtil.BET_50);
        } else if (i == R.id.bet1000) {
            setBetUI(bet1000, GameAnimUtil.BET_100);
        } else if (i == R.id.bet5000) {
            setBetUI(bet5000, GameAnimUtil.BET_500);
        }else if(i == R.id.iv_menu){
            if (isPublish) {
                showMenuWindow(iv_menu);
            } else {
                openHistoryDialog(getGameType());
            }
        }
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

    protected void setBetUI(ImageView iv_bet, int bet) {
        currentBet = bet;
        if (bet > mMy_diamonds) {
            GameUIUtils.showCheckMoney();
            return;
        }
        mMy_diamonds = getMoney();
        notityCradUI(mMy_diamonds, currentBet, bet10, bet100, bet1000, bet5000);
//        }
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
                    EventBus.getDefault().post(new GoPayListActivityEvent());
                    dialog.dismiss();
                }
            });
            return false;
        }
        return true;
    }

    @Override
    public void showFireResult(boolean paramBoolean, int paramInt) {

    }

    @Override
    public void showFireError() {

    }

    @Override
    public void setAllBetText(AllBetPush allBetPush) {
        super.setAllBetText(allBetPush);
        List<AllBetPush.BetInfo> betInfos = allBetPush.getBet_info();
        int one = ((AllBetPush.BetInfo) betInfos.get(0)).getAll_bet_number();
        int two = ((AllBetPush.BetInfo) betInfos.get(1)).getAll_bet_number();
        int three = ((AllBetPush.BetInfo) betInfos.get(2)).getAll_bet_number();
        if ((one > 0) && (one > this.previousLeftAll)) {
            this.previousLeftAll = one;
            String oneStr = one == 0 ? "0" : " " + String.valueOf(one) + " ";
            this.tv_bet_all_left.setText(oneStr);
        }
        if ((two > 0) && (two > this.previousCenterAll)) {
            this.previousCenterAll = two;
            String twoStr = two == 0 ? "0" : " " + String.valueOf(two) + " ";
            this.tv_bet_all_center.setText(twoStr);
        }
        if ((three > 0) && (three > this.previousRightAll)) {
            this.previousRightAll = three;
            String threeStr = three == 0 ? "0"  : " " + String.valueOf(three) + " ";
            this.tv_bet_all_right.setText(threeStr);
        }
    }

    @Override
    public void setMyBetText(int one, int two, int three) {
        switch (one){
            case 0:
                this.tv_bet_me_left.setText(" " + String.valueOf(two) + " ");
                this.tv_bet_all_left.setText(" " + String.valueOf(three) + " ");
                break;
            case 1:
                this.tv_bet_me_center.setText(" " + String.valueOf(two) + " ");
                this.tv_bet_all_center.setText(" " + String.valueOf(three) + " ");
                break;
            case 2:
                this.tv_bet_me_right.setText(" " + String.valueOf(two) + " ");
                this.tv_bet_all_right.setText(" " + String.valueOf(three) + " ");
                break;
            default:
        }
    }

    @Override
    public void showXiaZhuPush(XiaZhuPush result) {
        super.showXiaZhuPush(result);
        LogUtils.d("赛车   下注");
        game_car_end_view.setVisibility(GONE);
        pb_black_car_end.setVisibility(GONE);
        pb_red_car_end.setVisibility(GONE);
        pb_yellow_car_end.setVisibility(GONE);
        iv_end.setVisibility(GONE);
        game_state = GAME_STATE_XIA_ZHU;
        iv_sai_che.setVisibility(GONE);
        beforeNext();
        djsAction(result.getCountdown_time());
    }

    @Override
    public void doXiaZhu(GameSound gameSound, XiaZhuPush xiaZhuPush) {
        super.doXiaZhu(gameSound, xiaZhuPush);
        LogUtils.d("赛车   下注");
        game_car_end_view.setVisibility(GONE);
        pb_black_car_end.setVisibility(GONE);
        pb_red_car_end.setVisibility(GONE);
        pb_yellow_car_end.setVisibility(GONE);
        iv_end.setVisibility(GONE);
        game_state = GAME_STATE_XIA_ZHU;
        iv_sai_che.setVisibility(GONE);
        beforeNext();
        djsAction(xiaZhuPush.getCountdown_time());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onXiaZhuEvent(XiaZhuPush xiaZhuPush) {
        if (!this.isPublish) {
            doXiaZhu(null, xiaZhuPush);
        }
    }

    @Override
    public void betResult(BetRespond betRespond) {
        super.betResult(betRespond);
        setMoneyTextView(betRespond.getMy_diamonds(), 0L);
        switch (betRespond.getPosition_id()) {
            case BET_LEFT:
                tv_bet_me_left.setText(" " + betRespond.getMy_bet_number() + " ");
//                showMybetAnim(cbv_bet_left, betRespond.getCurrentBet());
                break;
            case BET_CENTER:
                tv_bet_me_center.setText(" " + betRespond.getMy_bet_number() + " ");
//                showMybetAnim(cbv_bet_center, betRespond.getCurrentBet());
                break;
            case BET_RIGHT:
                tv_bet_me_right.setText(" " + betRespond.getMy_bet_number() + " ");
//                showMybetAnim(cbv_bet_right, betRespond.getCurrentBet());
            default:
                break;
        }

    }

    protected void showMybetAnim(final CrazyCarBetView cbvBet, final int bet) {

        cbvBet.addMyBet(bet, new GameAction().new Three<ImageView, Integer, Integer>() {
            @Override
            public void invoke(final ImageView imageView, Integer leftMargin, Integer topMargin) {
                ImageView IV_bet = null;

                switch (bet) {
                    case GameAnimUtil.BET_10:
                        IV_bet = bet10;
                        break;
                    case GameAnimUtil.BET_50:
                        IV_bet = bet100;
                        break;
                    case GameAnimUtil.BET_100:
                        IV_bet = bet1000;
                        break;
                    case GameAnimUtil.BET_500:
                        IV_bet = bet5000;
                        break;
                    default:
                        IV_bet = bet10;
                        break;
                }
                GameAnimUtil.myAddBet(IV_bet, imageView, root_container, bet, leftMargin, topMargin, new GameAnimUtil.GameLiveUtilListener() {
                    @Override
                    public void animEnd() {
//                        cbv_bet_left.showMyBetView(imageView);
                    }
                });
            }
        });
    }

    @Override
    public void showKaiPaiPush(GameKaiPaiPush result) {
        super.showKaiPaiPush(result);
        LogUtils.d("赛车   游戏switch_type="+result.getSwitch_type());
        if(result.getSwitch_type()!=-1){
            LogUtils.d("赛车   切换游戏");
            game_state = GAME_STATE_SWITCH;
            switch_type = result.getSwitch_type();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameResultEvent(GameResultPush gameResultPush) {

        String key = GameObserver.getAppObserver().getStringText(GameC.str.MY_DIAMONDS);
        GameObserver.getAppObserver().setObject(key, Integer.valueOf(gameResultPush.getMy_diamonds()));

        initnotityCradUI(mMy_diamonds, currentBet, bet10, bet100, bet1000, bet5000);

        mMyWinMoney = gameResultPush.getWin_money();

        if(mMyWinMoney>0){
            switch (win_id){
                case 1:
                    showWinAnim(iv_win_left);
                    break;
                case 2:
                    showWinAnim(iv_win_center);
                    break;
                case 3:
                    showWinAnim(iv_win_right);
                    break;
                default:
            }
        }

        if (mWin_money_position == null) {
            mWin_money_position = new ArrayList<>();
        }
        if (getGameType() == GameC.Game.GAME_TYPE_HLNZ && gameResultPush.getWin_money_position() != null) {
            mWin_money_position.clear();
            mWin_money_position.addAll(gameResultPush.getWin_money_position());
        }

        setMoneyTextView(gameResultPush.getMy_diamonds(), time_win_delay);


    }

    @Override
    public void setMoneyTextView(final int money, long delayed) {
        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setMoney(money);
//                setBetImage(money);
            }
        }, delayed);
    }

    /**
     * 设置我的钻石
     *
     * @param money 我的钻石
     */
    protected void setBetImage(int money) {
        currentBet = BottomBarUtil.setBetImage(bet10, bet100, bet1000, bet5000, money, currentBet);
    }

    Runnable endGame = new Runnable() {
        @Override
        public void run() {
            if(isPublish){
                if(game_state == GAME_STATE_END){
                    LogUtils.d("赛车   开始一下局");
                    mPresenter.startGame(getGameType());
                }else if(game_state == GAME_STATE_SWITCH){
                    EventBus.getDefault().post(new GameStartEvent(switch_type));
                }
            }else {
                LogUtils.d("直播结束 liveIsEnd = "+liveIsEnd);
                if(liveIsEnd){
                    EventBus.getDefault().post(new CarEndLiveEndEvent());
                    liveIsEnd = false;
                    return;
                }
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLiveNoEndEvent(LiveNoEndEvent liveNoEndEvent){
        LogUtils.d("直播结束 liveNoEndEvent");
        liveIsEnd = true;
    }


    @Override
    public void showendGameError() {
        super.showendGameError();
        LogUtils.e("showendGameError     重新连接(主播调用)");
        if ((this.mHandler != null) &&
                (this.isPublish)) {
            this.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (BaseCarLayout.this.mPresenter != null) {
                        BaseCarLayout.this.mPresenter.endGame(getGameType());
                    }
                }
            }, 3000L);
        }
    }

    @Override
    public void showStartGameError() {
        super.showStartGameError();
        LogUtils.e("showStartGameError     重新连接(主播调用)");
        if ((this.mHandler != null) &&
                (this.isPublish)) {
            this.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (BaseCarLayout.this.mPresenter != null) {
                        BaseCarLayout.this.mPresenter.startGame(BaseCarLayout.this.getGameType());
                    }
                }
            }, 3000L);
        }
    }

    @Override
    public void showCarDataError() {
        LogUtils.e("showCarDataError     重新连接(主播调用)");
        if ((this.mHandler != null) &&
                (this.isPublish)) {
            this.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (BaseCarLayout.this.mPresenter != null) {
                        getCarData();
                    }
                }
            }, 3000L);
        }
    }

    protected int getGameType() {
        return GameC.Game.GAME_TYPE_CRAZYCAR;
    }


    protected void showWinAnim(final ImageView iv){
        GameAnimUtil.startWinAnim(this, iv, money, false);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.startWinAnim(BaseCarLayout.this, iv, money, false);
            }
        }, 100);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.startWinAnim(BaseCarLayout.this, iv, money, false);
            }

        }, 200);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.startWinAnim(BaseCarLayout.this, iv, money, false);

            }
        }, 300);
    }


}
