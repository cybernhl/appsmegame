package weking.lib.game.view.game_2;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.bean.GiftListBean;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.event.GoPayListActivityEvent;
import weking.lib.game.manager.memory.GameSoundBg;
import weking.lib.game.manager.memory.GameSoundZww;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.presenter.AnimatorFactory;
import weking.lib.game.utils.FastClickUtil;
import weking.lib.game.utils.GameAnimUtil;
import weking.lib.game.utils.GameUIUtils;
import weking.lib.game.utils.GameUtil;
import weking.lib.utils.LogUtils;

import static android.animation.ObjectAnimator.ofFloat;
import static weking.lib.game.GameC.str.MY_DIAMONDS;
import static weking.lib.game.utils.GameUtil.getMoney;

/**
 * 创建时间 2017/6/16.
 * 创建人 frs
 * 功能描述
 */
public class ZWWLayout extends BaseGame_2Layout implements View.OnClickListener {
    private static final String TAG = "ZWWLayout";
    private Context mContext;

    private BaseGameZWWRollView mRool_view_top;
    private BaseGameZWWRollView mRool_view_buttom;


    //底部bar
    private ImageView mIv_fire;
    private ImageView iv_money;
    private ImageView iv_bet10;
    private ImageView iv_bet100;
    private ImageView iv_bet1000;
    //底部bar

    //    抓子
    private RelativeLayout mRl_zhua;  // 抓子
    private ImageView iv_zhou;   // 轴
    private ImageView iv_line;  //线
    private ImageView iv_zhua_righ;  //钩子
    private ImageView iv_zhua_left;
    private ImageView iv_connect;  //钩子与线的连接
    //    抓子
    //礼物
    private View mView_roll_item;  // 钩子上的物品
    private ImageView iv_light;  //光
    private ImageView iv_light_bg;  //光背景
    private RelativeLayout rl_gaem;  //  游戏背景
    private ImageView iv_head;  //勾上面的图片
    private ImageView iv_gift;  //勾上面的礼物图片
    private TextView tv_hook_money;  //勾上面的金额
    //礼物
    //对象
    private AnimatorSet finalSet = new AnimatorSet();

    private int[] mGiftBGData;// 头像数据
    private View rollItemView;    //被抓的条目
    private Random mRandom;

    //时间
    private long animTime = 1000L; // 下去 , 上去的时间
    private long zhuaUpAnimTime = animTime * 3; // 抓子上去的时间
    private long zhuaUpAnimTimeTo1000 = animTime * 3 / 1000; // 抓子上去的时间
    private long mZhuaUpSpeed;   // 钩子上去是速度
    private long mZhuaTiem = 200;   // 抓子在下面抓的时间
    private long mYaoTiem = 1500;   // 抓子在摇一摇的时间
    private long diaoMinTiem = 1000; //随机掉下来的距离

    //时间


    //配置高度
    private int mRollViewHeight;     //转动的物品高度
    private int marginTopHeight = 450;   //绳子 距离屏幕高度
    private int mWindowHeight;
    private int mWindowWidth;
    private int mWindowWidthTO2;
    private int animDuration = 13000;//执行动画的时间
    private float mRollItemWidth;  // 滚动条目的width
    private float mRollItemWidthTO2;  // 滚动条目的width
    private long mRollItemScrollWidthLeft;   //可以抓住的点Left
    private long mRollItemScrollWidthRight;  //可以抓住的点Right
    private int mZhuaStateHeight;  //钩子距离屏幕顶端的位置
    private int mRandomDiaoHeight = 400; //随机掉下来的距离


    //配置高度

    //旋转角度
    private float zhuaPivotMinAngle = 10F; //抓子转动最小角度
    private float zhuaPivotMaxAngle = 10F; //抓子转动最大角度
    private float shouPivotMinAngle = 15F; //回去转动最小角度
    private float shouPivotMaxAngle = 15F; //回去转动最大角度
    //旋转角度

    //索引 和状态
    public int rollPosition;  // 滚动回调的 postion
    public int rollCurValueX = 0;// 滚动横向位置的 postion
    public int rollCurValueY = 0;// 滚动横向位置的 postion
    public int zhuaType = -1;// 抓的状态 0 默认  -1 失败  1  成功  2 抓空  3 正在请求接口
    public int animType = 0; // 动画执行阶段状态  0 默认 , 1 下   , 2抓 , 3  上, 4 停止
    private boolean isDownHook;  //下勾动画是否可以下
    private boolean isFire;  //是否在请求接口
    private boolean isShowFinalAnim = false;  // 本钱

    //索引 和状态

    public static int[] ResId = new int[]{
            R.drawable.game_zww_ls,
            R.drawable.game_zww_nn,
            R.drawable.game_zww_lh,
            R.drawable.game_zww_tz,
            R.drawable.game_zww_xl,
            R.drawable.game_zww_xs,
            R.drawable.game_zww_m,
            R.drawable.game_zww_y,
            R.drawable.game_zww_hz,
            R.drawable.game_zww_jj,
            R.drawable.game_zww_gg,
            R.drawable.game_zww_zz,

    };
    private ArrayList<AnimatorSet> mAnimSetList;
    private AnimatorSet mZhuaSet; // 抓
    private AnimatorSet mDownSet;  //下
    private AnimatorSet mUpSet;   // 收
    private AnimatorSet mRollItemTrackset;  // 礼物上下移动的轨迹
    private AnimatorSet mZhuaItemMoveErrorSet;  // 抓和itme移动  失败
    private AnimatorSet mZhuaItemMoveSucceedSet;// 抓和itme移动  成功
    private AnimatorSet mShowSucceedSet;       // 显示成功
    private AnimatorSet mImteToOriginalSet;    //  礼物 下落消失


    public ZWWLayout(Context context, boolean is_Publish) {
        this(context, null, is_Publish);

    }

    public ZWWLayout(Context context, AttributeSet attrs, boolean is_Publish) {
        super(context, attrs);
        isPublish = is_Publish;
//        mPresenter = new Game2Preserter(this);
        mAnimSetList = new ArrayList<>();
        this.mContext = context;
        mWindowHeight = GameUtil.getWindowHeight(mContext);
        mWindowWidth = GameUtil.getWindowWidth(mContext);
        mWindowWidthTO2 = mWindowWidth / 2;
        mRollItemWidth = GameUtil.getDimension(R.dimen.game_zww_item_iv_width);
        mRollItemWidthTO2 = mRollItemWidth / 2;
        marginTopHeight = (int) (mWindowHeight / 2.7);
        mZhuaStateHeight = -mWindowHeight + marginTopHeight;
        mZhuaUpSpeed = Math.abs(mZhuaStateHeight) / zhuaUpAnimTimeTo1000;
        //下勾动画执行完 , roolview 的移动距离 左边
        int animDurationTO1000 = animDuration / 1000;
        int animTimeTO1000 = (int) (animTime / 1000);
        mRollItemScrollWidthLeft = (mWindowWidth / animDurationTO1000) * animTimeTO1000;
        mRollItemScrollWidthRight = (long) (mRollItemScrollWidthLeft + mRollItemWidth);
        //初始化状态抓子上去的动画
        mRandom = new Random();
        mGiftBGData = ResId;

        View view = LayoutInflater.from(context).inflate(R.layout.game_layout_main_zww, null);
        this.addView(view);
        initView(view);
        initAnimState();
        initAnim();
        if (!isPublish) {
            initBuMusic();
        }
    }

    /**
     * chu初始化声音
     */
    private void initBuMusic() {
        if (!isOpneGameBgSound) {
//            ((GameSoundBgtext) GameSoundBgtext.getInstance()).playZWWBg(mContext);
        }
    }


    // 初始化 设置数据
    @Override
    public void startGameDollData(List<List<XiaZhuPush.GameBaen>> game_info) {
        int diamonds = getMoney();
        setMoney(diamonds);

        ininData(game_info);
//        currentBet = GameLiveUtil.setZWWBetImage(iv_bet10, iv_bet100, iv_bet1000, mMy_diamonds, currentBet);
        mRool_view_top.startAnim();
        mRool_view_buttom.startAnim();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_gaem || i == R.id.iv_fire) {
            downHook();

        } else if (i == R.id.chongzhi) {
            EventBus.getDefault().post(new GoPayListActivityEvent());

        } else if (i == R.id.bet10) {
            if (!isinitMoneyBet && bet10 > mMy_diamonds) {
                GameUIUtils.showCheckMoney();
                return;
            }
            if (!isDownHook) {
                LogUtils.i("---点击10倍，游戏中");
                return;
            }
            currentBet = bet10;
            clickBet(currentBet);

        } else if (i == R.id.bet100) {
            if (!isinitMoneyBet && bet100 > mMy_diamonds) {
                GameUIUtils.showCheckMoney();
                return;
            }
            if (!isDownHook) {
                LogUtils.i("---点击100倍，游戏中");
                return;
            }
            currentBet = bet100;
            clickBet(currentBet);

        } else if (i == R.id.bet1000) {
            if (!isinitMoneyBet && bet1000 > mMy_diamonds) {
                GameUIUtils.showCheckMoney();
                return;
            }
            if (!isDownHook) {
                LogUtils.i("---点击1000倍，游戏中");
                return;
            }
            currentBet = bet1000;

            clickBet(currentBet);

        }
        isinitMoneyBet = false;
    }

    private boolean isMinusBet = false; // 减倍数

    //更新 倍数
    private void clickBet(int bet) {
//        if (!GameLiveUtil.checkMoney(bet)) {
//            return;
//        }
        if (FastClickUtil.isFastClick(200)){
            return;
        }

        if (isDownHook || isMinusBet) {
            isMinusBet = false;
            setData(bet);
            GameUIUtils.notityBetUI(bet
                    , iv_zhou,   // 轴
                    iv_line,  //线
                    iv_zhua_righ,  //钩子
                    iv_zhua_left,
                    iv_connect,  //钩子与线的连接
                    view_toolbar,  //钩子与线的连接
                    iv_bet10,
                    iv_bet100,
                    iv_bet1000,
                    mMy_diamonds
            );
        }
    }


    // 下钩
    private void downHook() {
        LogUtils.d("  zhuaType   downHook   " + zhuaType);
        if (!GameUIUtils.checkMoney(mMy_diamonds)) {
            return;
        }

        if (isDownHook) {
            //更新 倍数

            isDownHook = false;
            LogUtils.d("aaaa   downHook  "+isDownHook);
            isinitMoneyBet = true;
            if (GameAnimUtil.BET_100 < mMy_diamonds && mMy_diamonds < GameAnimUtil.BET_1000) {
                if (currentBet >= GameAnimUtil.BET_100) {
                    onClick(iv_bet100);
                    isMinusBet = true;
                }
            } else if (mMy_diamonds <= GameAnimUtil.BET_100 && mMy_diamonds > GameAnimUtil.BET_10) {
                if (currentBet >= GameAnimUtil.BET_10) {
                    onClick(iv_bet10);
                    isMinusBet = true;
                }
            }
            setFireState(true);

            int diamonds = GameUtil.getMoney();
            setMoney(diamonds - currentBet);
//            currentBet = Game69Utils.zwwJianBetUi(currentBet, mMy_diamonds, iv_bet10, iv_bet100, iv_bet1000);
            mIv_fire.setImageResource(R.drawable.icon_button_n_n_play);

            mPresenter.fire(
                    zhuaType == 1 ? true : false,
                    mapIndex.get(rollPosition),
                    currentBet);

            //开始下勾
            finalSet.start();
            //  动画执行阶段状态  0 默认 , 1 下   , 2抓 , 3 上, 4 停止
            animType = 1;
            // 抓的状态 0 默认  -1 失败  1  成功  2 抓空  3 正在请求接口
            if (zhuaType == 0 || zhuaType == 2) { // 抓空
            } else {
                zhuaType = 3;
            }
            if(mGameSound!=null){
                mGameSound.playSound(GameSoundZww.GAME_ZWW_DOWN, 0, 5);
            }
        }
    }

    @Override
    public void showFireError() {
        if (animType == 4) {   // 娃娃停住
            finalExamineAnim(false);
        } else {
            if (zhuaType != 2) { //抓空
                zhuaType = -1;
            }
        }
    }


    @Override
    public void showFireResult(final boolean hit, final int my_diamonds) {
        mMy_diamonds = my_diamonds;
        setMoneyTextView(mMy_diamonds, 0);
        // // 抓的状态 0 默认  -1 失败  1  成功  2 抓空  3 正在请求接口
        if (animType == 4) {   // 娃娃停住
            if (hit) {
                finalExamineAnim(true);
            } else {
                finalExamineAnim(false);
            }
        } else {  //出来 抓空 不做判断
            if (zhuaType == 1 || zhuaType == -1 || zhuaType == 3) {
                zhuaType = (hit ? 1 : -1);
            }
        }

    }


    @Override
    public void setMoneyTextView(int diamonds, long delayed) {
        super.setMoneyTextView(diamonds, delayed);
        this.mMy_diamonds = diamonds;
        if (delayed == 0) {// 点击时候的刷新
            GameUIUtils.initnotityZWWUI(diamonds, currentBet, iv_bet10, iv_bet100, iv_bet1000);
        } else { // 充值的回来刷新
            if (currentBet == GameAnimUtil.BET_1000) {
                onClick(iv_bet1000);
                isMinusBet = true;
            } else if (currentBet == GameAnimUtil.BET_100) {
                onClick(iv_bet100);
                isMinusBet = true;
            } else if (currentBet == GameAnimUtil.BET_10) {
                onClick(iv_bet10);
                isMinusBet = true;
            }
        }
    }
// 初始化动画状态

    private void initAnimState() {
        AnimatorSet enter = new AnimatorSet();
        if (!mAnimSetList.contains(enter)) {
            mAnimSetList.add(enter);
        }
        ObjectAnimator initUPLineAnimator = ofFloat(mRl_zhua, "translationY", 0, mZhuaStateHeight);
        ObjectAnimator initUPZhuaLeftAnimator = ofFloat(iv_zhua_left, "translationY", 0, mZhuaStateHeight);
        ObjectAnimator initUPAnimator = ofFloat(iv_line, "translationY", 0, mZhuaStateHeight);
        enter.setDuration(0);
        enter.playTogether(initUPAnimator, initUPLineAnimator, initUPZhuaLeftAnimator);
        enter.start();
        //初始化抓子
        AnimatorSet zhuaSet = initZhuaSet(0);
        zhuaSet.start();
    }

    private void initAnim() {
        // 抓
        mZhuaSet = getZhuaSet(mZhuaTiem);
        // 勾下去
        mDownSet = getDownAnimtor();
        // 勾上去
        mUpSet = getUpAnimtor();
        mUpSet.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationEnd(
                    Animator nimation) {
                //抓空时候
                LogUtils.d("zhuaType:   // 勾上去   isDownHook  " + isDownHook);
                if (zhuaType == 2 || zhuaType == 0) {
                    isDownHook = true;
                    LogUtils.d("aaaa    initAnim  "+isDownHook);
                    showImFireRes();
//                    LogUtils.d("zhuaType:   // 勾上去   isDownHook  " + isDownHook);

                    setFireState(false);
                }
            }
        });
        mDownSet.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mView_roll_item.setVisibility(VISIBLE);

                GiftListBean giftListBean = GameObserver.getAppObserver().getGiftBean(mapIndex.get(rollPosition));
                iv_head.setBackgroundResource(mGiftBGData[rollPosition]);
                tv_hook_money.setText(giftListBean.price + "");
                GameObserver.getAppObserver().loaderImage(iv_gift, giftListBean.pic_url);
                tv_hook_money.setText(GameObserver.getAppObserver().getGiftBean(mapIndex.get(rollPosition)).price + "");
                LogUtils.d("zhuaType  down  " + zhuaType);
                //  动画执行阶段状态  0 默认 , 1 上   , 2抓 , 3 下, 4 停止
                animType = 2;
                // 抓的状态 0 默认  -1 失败  1  成功  2 抓空  3 正在请求接口
                if (zhuaType == 1 || zhuaType == 3) {
                    // 隐藏条目
                    if (rollItemView != null) {
                        rollItemView.setVisibility(INVISIBLE);
                    }
                    moveSucceedRoolItemAnim(rollPosition);
                } else if (zhuaType == -1) {
                    if (rollItemView != null) {
                        rollItemView.setVisibility(INVISIBLE);
                    }
                    moveErrorRoolItemAnim(rollPosition);
//                    Toast.makeText(mContext, "失败了 " + rollPosition, Toast.LENGTH_SHORT).show();
                } else if (zhuaType == 3) {
//                    moveSucceedRoolItemAnim(rollPosition);
                } else {
                    mView_roll_item.setVisibility(INVISIBLE);
                }
            }

        });
//        finalSet   .setInterpolator(new AccelerateDecelerateInterpolator());
        finalSet.playSequentially(mDownSet, mZhuaSet, mUpSet);
    }

    private void showImFireRes() {
        mIv_fire.setImageResource(R.drawable.selector_game_zww_start);
    }

    // 开始抓 移动item<失败>
    private void moveErrorRoolItemAnim(int rollPosition) {

        mRollItemTrackset = new AnimatorSet();  // 礼物移动轨迹
        //随机高度
        int randomHeight = mRandom.nextInt(mRandomDiaoHeight);

        mZhuaItemMoveErrorSet = new AnimatorSet();
        mView_roll_item.setPivotX(mView_roll_item.getMeasuredWidth() / 2);
        int tranAnimStart = mWindowWidthTO2 - rollCurValueX;
        ObjectAnimator zhuzLeftAnim = null;
        if (mWindowWidthTO2 > (rollCurValueX + mRollItemWidthTO2)) {
            tranAnimStart = (int) (-tranAnimStart + mRollItemWidthTO2);
            zhuzLeftAnim = ofFloat(mView_roll_item, "rotation", shouPivotMaxAngle, -shouPivotMinAngle, 0);//360度旋转
        } else {
            zhuzLeftAnim = ofFloat(mView_roll_item, "rotation", -shouPivotMaxAngle, shouPivotMinAngle, 0);//360度旋转
        }
        final ObjectAnimator tranAnim = ofFloat(mView_roll_item, "translationX", tranAnimStart, 0);
        final ObjectAnimator tranAnimY = ofFloat(mView_roll_item, "translationY", rollCurValueY, 0);
//        随机时间
        long randomDiaoTime = (randomHeight * 1000 / mZhuaUpSpeed);

        // 抓上去停止的高度
        long upStopHeight = (mZhuaUpSpeed * (diaoMinTiem / 1000) + randomHeight);
        // 抓上去停止的时间
        long upStopTime = diaoMinTiem + randomDiaoTime;
        final AnimatorSet downSet = getDownAnim(upStopHeight, upStopTime);
        downSet.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if(mGameSound!=null){
                    mGameSound.playSound(GameSoundZww.GAME_ZWW_ERROR, 0, 5);
                }
            }
        });
        final ObjectAnimator tranYAnim = ofFloat(mView_roll_item, "translationY", 0, -upStopHeight);
        mRollItemTrackset.addListener(new AnimatorFactory() {

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.d("zhuaType:   // 勾失败 isDownHook  " + isDownHook);
                isDownHook = true;
                showImFireRes();

                setFireState(false);
            }
        });
        tranYAnim.setDuration(upStopTime);
        tranYAnim.setInterpolator(new LinearInterpolator());
        tranAnim.setDuration(mZhuaTiem);
        tranAnimY.setDuration(mZhuaTiem);


        tranAnim.addListener(new AnimatorFactory() {

            @Override
            public void onAnimationEnd(Animator animator) {
                mRollItemTrackset.play(tranYAnim).before(downSet);
                mRollItemTrackset.start();
            }


        });
        zhuzLeftAnim.setDuration(mYaoTiem);
        mZhuaItemMoveErrorSet.playTogether(tranAnim, zhuzLeftAnim);
        mZhuaItemMoveErrorSet.start();
    }

    // 开始抓 移动item<成功>
    private void moveSucceedRoolItemAnim(int rollPosition) {
        mZhuaItemMoveSucceedSet = getMoveRotationSet();
        mZhuaItemMoveSucceedSet.start();
    }


    // 抓过后的移动的旋转
    private AnimatorSet getMoveRotationSet() {
        AnimatorSet set = new AnimatorSet();
        mView_roll_item.setPivotX(mView_roll_item.getMeasuredWidth() / 2);
        mView_roll_item.setPivotY(20);
        int tranAnimStart = mWindowWidthTO2 - rollCurValueX;
        ObjectAnimator zhuzLeftAnim = null;
        if (mWindowWidthTO2 > (rollCurValueX + mRollItemWidthTO2)) {
            tranAnimStart = (int) (-tranAnimStart + mRollItemWidthTO2);
            zhuzLeftAnim = ofFloat(mView_roll_item, "rotation", shouPivotMaxAngle, -shouPivotMinAngle, 0);//360度旋转
        } else {
            zhuzLeftAnim = ofFloat(mView_roll_item, "rotation", -shouPivotMaxAngle, shouPivotMinAngle, 0);//360度旋转
        }

        //横向移动娃娃
        final ObjectAnimator tranAnim = ofFloat(mView_roll_item, "translationX", tranAnimStart, 0);

        final ObjectAnimator tranYAnim = ofFloat(mView_roll_item, "translationY", 0, mZhuaStateHeight);
        // 娃娃移动后的
        tranYAnim.addListener(new AnimatorFactory() {

            @Override
            public void onAnimationEnd(Animator animator) {
                //  动画执行阶段状态  0 默认 , 1 下   , 2 抓 , 3 上, 4 停止
                Log.d(TAG, "zhuaType  最后 : " + zhuaType + "  zhuaType   " + zhuaType);
//                isShowFinalAnim = false;
                if (zhuaType == 1) {   //成功

                    finalExamineAnim(true);
                } else if (zhuaType == -1) {// 接口没有返回  ||返回失败了
                    finalExamineAnim(false);
                } else {
                    animType = 4;
                }
            }
        });
        tranYAnim.setDuration(zhuaUpAnimTime);
        tranYAnim.setInterpolator(new LinearInterpolator());
        tranAnim.setDuration(mZhuaTiem);
        tranAnim.addListener(new AnimatorFactory() {


            @Override
            public void onAnimationEnd(Animator animator) {
                tranYAnim.start();

            }
        });
        zhuzLeftAnim.setDuration(mYaoTiem);
        set.playTogether(zhuzLeftAnim, tranAnim);
        return set;
    }

    // 在最上面判断动画
    private void finalExamineAnim(final boolean isSucceed) {

        if (isSucceed) {  // 成功抓起
            mShowSucceedSet = getScaleAndTranAnim();
            mShowSucceedSet.start();
        } else {  //没有调到接口回到初始化值
            if(mGameSound!=null){
                mGameSound.playSound(GameSoundZww.GAME_ZWW_ERROR, 0, 5);
            }
            mImteToOriginalSet = new AnimatorSet();
            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mView_roll_item, "alpha", 1, 0);
            ObjectAnimator tranAnim = ObjectAnimator.ofFloat(mView_roll_item, "translationY", mZhuaStateHeight, 0);
            alphaAnim.setDuration(zhuaUpAnimTime);
            tranAnim.setDuration(zhuaUpAnimTime);
            mImteToOriginalSet.playTogether(tranAnim, alphaAnim);
            mImteToOriginalSet.start();
            mImteToOriginalSet.addListener(new AnimatorFactory() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    LogUtils.d("zhuaType:   // 没有调到接口  isDownHook  " + isDownHook);
                    goToOrigin();

                }
            });
        }
    }

    // 回去最初的状态
    public void goToOrigin() {
        ObjectAnimator.ofFloat(mView_roll_item, "alpha", 0, 1).setDuration(0).start();
        isDownHook = true;
        showImFireRes();

        setFireState(false);
        mView_roll_item.setVisibility(GONE);
    }

    //完成后的显示
    private void startSucceedPerform() {


    }

    AnimatorSet shouGiftAnim;   //显示成功动画,后收回的的动画

    // 显示完了回去动画
    public AnimatorSet getScaleAndReturnAnim() {
        if (shouGiftAnim != null) {
            return shouGiftAnim;
        }
        shouGiftAnim = new AnimatorSet();
        mAnimSetList.add(shouGiftAnim);
        ObjectAnimator tranYAnim = ObjectAnimator.ofFloat(mView_roll_item, "translationY", mZhuaStateHeight / 2, 300);
        ObjectAnimator tranXAnim = ObjectAnimator.ofFloat(mView_roll_item, "translationX", 0, -mWindowWidth / 2 + 150);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mView_roll_item, "scaleX", 1.5F, 0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mView_roll_item, "scaleY", 1.5F, 0);
        shouGiftAnim.setDuration(500);
        shouGiftAnim.playTogether(tranXAnim, tranYAnim, scaleX, scaleY);
        return shouGiftAnim;
    }

    AnimatorSet finalSucceedSet;

    //完成后的显示
    public AnimatorSet getScaleAndTranAnim() {
        if (finalSucceedSet != null) {
            return finalSucceedSet;
        }
        final AnimatorSet returnSet = getScaleAndReturnAnim();

        returnSet.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.d("zhuaType:   // 成功   isDownHook  " + isDownHook);
                goToOrigin();
                AnimatorSet shouOriginSet = shouOriginSet();
                shouOriginSet.addListener(new AnimatorFactory() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setMoney(mMy_diamonds);
                    }
                });
                shouOriginSet.start();

            }
        });
        finalSucceedSet = new AnimatorSet();
        mAnimSetList.add(finalSucceedSet);
        ObjectAnimator tranAnim = ObjectAnimator.ofFloat(mView_roll_item, "translationY", mZhuaStateHeight, mZhuaStateHeight / 2);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mView_roll_item, "scaleX", 1, 1.5F);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mView_roll_item, "scaleY", 1, 1.5F);
        finalSucceedSet.setDuration(1300);
        finalSucceedSet.playTogether(tranAnim, scaleX, scaleY);
        finalSucceedSet.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //旋转
                iv_light.setVisibility(VISIBLE);
                iv_light.setVisibility(VISIBLE);
                iv_light_bg.setVisibility(VISIBLE);
                if (mGameSound != null) {
                    mGameSound.playSound(GameSoundZww.GAME_ZWW_SUCCEED, 0, 5);
                }
                ObjectAnimator animator = ObjectAnimator.ofFloat(iv_light, "rotation", 0, 360);
                animator.setDuration(1500);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
                animator.addListener(new AnimatorFactory() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        mGameSound.stopSound(GameSoundZww.GAME_ZWW_SUCCEED);
//                        mGameSound.playSound(GameSoundZww.SOUND_WIN_GOLD, 0);
                        iv_light.setVisibility(INVISIBLE);
                        iv_light_bg.setVisibility(INVISIBLE);
                        //回去
                        returnSet.start();
                    }
                });
            }
        });
        return finalSucceedSet;
    }

    private AnimatorSet shouOriginSet;

    private AnimatorSet shouOriginSet() {
        if (shouOriginSet != null) {
            return shouOriginSet;
        }

        shouOriginSet = new AnimatorSet();
        mAnimSetList.add(shouOriginSet);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mView_roll_item, "scaleX", 0, 1).setDuration(0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mView_roll_item, "scaleY", 0, 1).setDuration(0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mView_roll_item, "translationY", 300, 0).setDuration(0);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(mView_roll_item, "translationX", -mWindowWidth / 2 + 100, 0).setDuration(0);
        shouOriginSet.playTogether(scaleX, translationY, translationX, scaleY);

        return shouOriginSet;
    }

    private void initView(View view) {

        //view_toolbar


        view_toolbar = (View) view.findViewById(R.id.view_toolbar);
        mIv_fire = (ImageView) view_toolbar.findViewById(R.id.iv_fire);
        iv_money = (ImageView) view_toolbar.findViewById(R.id.money);
        iv_bet10 = (ImageView) view_toolbar.findViewById(R.id.bet10);
        iv_bet100 = (ImageView) view_toolbar.findViewById(R.id.bet100);
        iv_bet1000 = (ImageView) view_toolbar.findViewById(R.id.bet1000);
        tv_money = (TextView) view_toolbar.findViewById(R.id.tv_money);

        view_toolbar.findViewById(R.id.chongzhi).setOnClickListener(this);
        view_toolbar.setOnClickListener(this);
        //view_toolbar


        //钩子
        mRl_zhua = (RelativeLayout) view.findViewById(R.id.rl_zhua_no);
        rl_gaem = (RelativeLayout) view.findViewById(R.id.rl_gaem);
        iv_line = (ImageView) view.findViewById(R.id.iv_line);
        iv_connect = (ImageView) view.findViewById(R.id.iv_connect);
        iv_zhou = (ImageView) view.findViewById(R.id.iv_zhou);
        iv_zhua_left = (ImageView) view.findViewById(R.id.iv_zhua_left);
        iv_zhua_righ = (ImageView) view.findViewById(R.id.iv_zhua_righ);

        iv_light = (ImageView) view.findViewById(R.id.iv_light);
        iv_light_bg = (ImageView) view.findViewById(R.id.iv_light_bg);
        mView_roll_item = view.findViewById(R.id.view_roll_item);
        iv_head = (ImageView) mView_roll_item.findViewById(R.id.iv_head);
        iv_gift = (ImageView) mView_roll_item.findViewById(R.id.iv_gift);
        tv_hook_money = (TextView) mView_roll_item.findViewById(R.id.tv_money);
        rl_gaem.setOnClickListener(this);
        mIv_fire.setOnClickListener(this);
        iv_bet10.setOnClickListener(this);
        iv_bet100.setOnClickListener(this);
        iv_bet1000.setOnClickListener(this);


        mRool_view_top = (BaseGameZWWRollView) findViewById(R.id.rool_view_top);
        mRool_view_top.setReverseRoll(true).setListenerAddUpdate(false)
                .setItemScale(0.7f, 0.7f)
                .setItmeBG(R.color.normal_text)
                .setGiftBGData(mGiftBGData)
                .initView(R.layout.item_game_roll_bottom_view);
        mRool_view_buttom = (BaseGameZWWRollView) findViewById(R.id.rool_view_bottom);
        mRool_view_buttom
                .setListenerAddUpdate(true)
                .setAnimDuration(animDuration)
//                .setBoundView(true)
//                .setItemScale(100, 500)
                .setGiftBGData(mGiftBGData)
                .setRollItemScrollSize(mRollItemScrollWidthRight, mRollItemScrollWidthLeft)
                .setAninListener(new BaseGameZWWRollView.AninListener() {


                    @Override
                    public void onAnimationUpdateSucceed(int curValue, int position, View view) {

                        int[] locationOnScreen = GameUtil.getLocationOnScreen(view);
                        int x = locationOnScreen[0];
                        int y = locationOnScreen[1];
                        if (!isFire) {
                            zhuaType = 1;
                        }
                        ZWWLayout.this.rollCurValueX = x;
                        ZWWLayout.this.rollCurValueY = y;
                        ZWWLayout.this.rollPosition = position;
                        ZWWLayout.this.rollItemView = view;
//                        Log.d(TAG, "onAnimationUpdateSucceed: Succeed   " + curValue + "  position    " + position + "   zhuaType  " + zhuaType);
                    }

                    @Override
                    public void onAnimationUpdateError(int curValue, int position, View view) {

                        int[] locationOnScreen = GameUtil.getLocationOnScreen(view);
                        int x = locationOnScreen[0];
                        int y = locationOnScreen[1];
                        if (!isFire) {
                            zhuaType = 2;
                        }
                        ZWWLayout.this.rollCurValueX = x;
                        ZWWLayout.this.rollCurValueY = y;
                        ZWWLayout.this.rollPosition = position;
                        ZWWLayout.this.rollItemView = view;
//                        Log.d(TAG, "onAnimationUpdateSucceed: Error   " + curValue + "  position    " + position + "   zhuaType  " + zhuaType);
                    }
                }).initView(R.layout.item_game_roll_bottom_view);
//        mMy_diamonds = SpUtil.getIntValue(MY_DIAMONDS, 0);
        String key = GameObserver.getAppObserver().getStringText(MY_DIAMONDS);
        mMy_diamonds = (int) GameObserver.getAppObserver().getObject(key, 0);
        GameUIUtils.initnotityZWWUI(mMy_diamonds, currentBet, iv_bet10, iv_bet100, iv_bet1000);
    }

    private void ininData(List<List<XiaZhuPush.GameBaen>> game_info) {
        HashMap<Integer, GiftListBean> giftMapMemory = GameObserver.getAppObserver().getGiftMap();
        if (giftMapMemory == null) {
            LogUtils.e("GiftListMemory.getGiftMapMemory() -- null");
            return;
        }
        List<XiaZhuPush.GameBaen> gameBet10Data = game_info.get(0);
        List<XiaZhuPush.GameBaen> gameBet100Data = game_info.get(1);
        List<XiaZhuPush.GameBaen> gameBet1000Data = game_info.get(2);

//        try {
        for (int i = 0; i < gameBet10Data.size(); i++) {
            mapIndex10.add(gameBet10Data.get(i).getId());
        }

        for (int i = 0; i < gameBet100Data.size(); i++) {
            mapIndex100.add(gameBet100Data.get(i).getId());
        }
        for (int i = 0; i < gameBet1000Data.size(); i++) {
            mapIndex1000.add(gameBet1000Data.get(i).getId());
        }

      /*  } catch (Exception e) {
            e.printStackTrace();
            new
        }*/


        mapIndex.clear();
        mapIndex.addAll(mapIndex10);
        mRool_view_buttom.setmData(mapIndex10);
        mRool_view_top.setmData(mapIndex10);
        isDownHook = true;
    }

    //设置数据
    public void setData(int bet) {
        mapIndex.clear();
        if (bet == 10) {
            iv_gift.setBackgroundResource(R.drawable.game_zww_board_10);
            mapIndex.addAll(mapIndex10);
            mRool_view_buttom.notifyData(mapIndex, bet10);
            mRool_view_top.notifyData(mapIndex, bet10);
        } else if (bet == 100) {
            iv_gift.setBackgroundResource(R.drawable.game_zww_board_100);
            mapIndex.addAll(mapIndex100);
            mRool_view_buttom.notifyData(mapIndex, bet100);
            mRool_view_top.notifyData(mapIndex, bet100);
        } else if (bet == 1000) {
            iv_gift.setBackgroundResource(R.drawable.game_zww_board_1000);
            mapIndex.addAll(mapIndex1000);
            mRool_view_buttom.notifyData(mapIndex, bet1000);
            mRool_view_top.notifyData(mapIndex, bet1000);
        } else {
            iv_gift.setBackgroundResource(R.drawable.game_zww_board_10);
            mapIndex.addAll(mapIndex10);
            mRool_view_buttom.notifyData(mapIndex, bet10);
            mRool_view_top.notifyData(mapIndex, bet10);
        }

    }

    AnimatorSet ZhuaSet = null;

    //抓子下去抓的动画
    private AnimatorSet getZhuaSet(long tiem) {

        if (ZhuaSet == null || tiem == 0) {
            ZhuaSet = new AnimatorSet();
            ObjectAnimator zhuzLeftAnim = ofFloat(iv_zhua_left, "rotation", zhuaPivotMaxAngle, -zhuaPivotMinAngle);//360度旋转
            ObjectAnimator zhuzRighAnim = ofFloat(iv_zhua_righ, "rotation", -zhuaPivotMaxAngle, zhuaPivotMinAngle);//360度旋转

            ZhuaSet.playTogether(zhuzLeftAnim, zhuzRighAnim);
            ZhuaSet.setDuration(tiem);
            return ZhuaSet;
        }
        return ZhuaSet;
    }

    //抓子下去抓的动画
    private AnimatorSet initZhuaSet(long tiem) {
        AnimatorSet ZhuaSet = new AnimatorSet();
        ObjectAnimator zhuzLeftAnim = ofFloat(iv_zhua_left, "rotation", zhuaPivotMaxAngle, -zhuaPivotMinAngle);//360度旋转
        ObjectAnimator zhuzRighAnim = ofFloat(iv_zhua_righ, "rotation", -zhuaPivotMaxAngle, zhuaPivotMinAngle);//360度旋转

        ZhuaSet.playTogether(zhuzLeftAnim, zhuzRighAnim);
        ZhuaSet.setDuration(tiem);
        return ZhuaSet;

    }


    //下去的动画
    private AnimatorSet getDownAnimtor() {
        AnimatorSet enter = new AnimatorSet();

        ObjectAnimator mZhuaAnim = ofFloat(mRl_zhua, "translationY", mZhuaStateHeight, 0);
        ObjectAnimator mXianAnim = ofFloat(iv_line, "translationY", mZhuaStateHeight, 0);
        ObjectAnimator mXianZhuaLeftAnim = ofFloat(iv_zhua_left, "translationY", mZhuaStateHeight, 0);
        ObjectAnimator fangLeftAnim = ofFloat(iv_zhua_left, "rotation", -zhuaPivotMinAngle, zhuaPivotMaxAngle);//360度旋转
        ObjectAnimator fangRighAnim = ofFloat(iv_zhua_righ, "rotation", zhuaPivotMinAngle, -zhuaPivotMaxAngle);//360度旋转

        iv_zhua_left.setPivotX(GameUtil.dip2px(mContext, 60));
        iv_zhua_left.setPivotY(GameUtil.dip2px(mContext, 20));
        iv_zhua_righ.setPivotX(GameUtil.dip2px(mContext, 20));
        iv_zhua_righ.setPivotY(GameUtil.dip2px(mContext, 20));

        enter.playTogether(mZhuaAnim, fangLeftAnim, fangRighAnim, mXianAnim, mXianZhuaLeftAnim);
        enter.setInterpolator(new LinearInterpolator());
        enter.setDuration(animTime);
        return enter;

    }

    AnimatorSet enter = null;

    //下勾上去的动画
    public AnimatorSet getUpAnimtor() {
        if (enter != null) {
            return enter;
        }
        enter = new AnimatorSet();
        mAnimSetList.add(enter);
        ObjectAnimator upAnimator = ofFloat(mRl_zhua, "translationY", 0, mZhuaStateHeight);
        ObjectAnimator upXianAnimator = ofFloat(iv_line, "translationY", 0, mZhuaStateHeight);
        ObjectAnimator upZhuaLeftAnimator = ofFloat(iv_zhua_left, "translationY", 0, mZhuaStateHeight);
        enter.setDuration(zhuaUpAnimTime);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(upAnimator, upXianAnimator, upZhuaLeftAnimator);
        return enter;
    }


    //失败掉下来的动画
    public AnimatorSet getDownAnim(long upStopHeight, long upStopTime) {

        AnimatorSet downSet = new AnimatorSet();

        final ObjectAnimator tranYAnim = ofFloat(mView_roll_item, "translationY", -upStopHeight, 0);
        final ObjectAnimator alphaAnim = ofFloat(mView_roll_item, "alpha", 1, 0);
        alphaAnim.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationEnd(Animator animator) {
                final ObjectAnimator alphaAnim = ofFloat(mView_roll_item, "alpha", 0, 1);
                alphaAnim.start();
                mView_roll_item.setVisibility(INVISIBLE);
            }
        });
        downSet.setDuration(upStopTime);
        downSet.playTogether(tranYAnim, alphaAnim);
        return downSet;
    }


    @Override
    public int getGameType() {
        return GameC.Game.GAME_TYPE_ZWW;
    }

    @Override
    protected void onDetachedFromWindow() {

//        if (!isPublish) {
//            GameCowboySound.getInstance().stopSound(GameCowboySound.GAME_BG_ZWW);
//        }

        mAnimSetList.add(mZhuaSet); // 抓
        mAnimSetList.add(mDownSet);  //下
        mAnimSetList.add(mUpSet);   // 收
        mAnimSetList.add(mRollItemTrackset);  // 礼物上下移动的轨迹
        mAnimSetList.add(mZhuaItemMoveErrorSet);  // 抓和itme移动  失败
        mAnimSetList.add(mZhuaItemMoveSucceedSet);// 抓和itme移动  成功
        mAnimSetList.add(mShowSucceedSet);       // 显示成功
        mAnimSetList.add(mImteToOriginalSet);    //  礼物 下落消失
        if (mAnimSetList != null && mAnimSetList.size() != 0) {
            for (AnimatorSet set : mAnimSetList) {
                if (set != null) {
                    set.cancel();
                    set.removeAllListeners();
                }
            }
        }
        if (!isPublish) {
            if (GameSoundBg.getNewInstance() != null) {
                GameSoundBg.getInstance().stopSound();
            }
        }
        if (mGameSound != null) {
            mGameSound.soundDestroy();
        }
//        setLayerType(View.LAYER_TYPE_NONE, null);
        super.onDetachedFromWindow();
    }

    /**
     * fire 接口状态
     *
     * @param fireState 正在请求接口   isFire = true;
     */
    public void setFireState(boolean fireState) {
        isFire = fireState;
        mRool_view_top.setFierState(fireState);

    }
}
