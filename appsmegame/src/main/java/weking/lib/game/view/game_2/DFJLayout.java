package weking.lib.game.view.game_2;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.event.GoPayListActivityEvent;
import weking.lib.game.manager.memory.GameSoundBg;
import weking.lib.game.manager.memory.GameSoundZww;
import weking.lib.game.presenter.AnimatorFactory;
import weking.lib.game.utils.GameAnimUtil;
import weking.lib.game.utils.GameUIUtils;
import weking.lib.game.utils.GameUtil;
import weking.lib.utils.anim.AnimImageUtil;

import static weking.lib.game.utils.GameUtil.getMoney;


/**
 * 创建时间 2017/6/16.
 * 创建人 frs
 * 功能描述
 */
public class DFJLayout extends BaseGame_2Layout implements View.OnClickListener {
    private static final String TAG = "ZWWLayout";

    private Context mContext;

    private GameDFJRollView mRool_view_top;
    private GameDFJRollView mRool_view_buttom;
    private RelativeLayout rl_game_bg;//  游戏背景
    private ImageView iv_gun;  // 枪
    private ImageView iv_gun_light;  // 枪
    private RelativeLayout rl_gun_all;  // 枪

    private View toolbar;//底部barniuniu

    private int mWindowHeight;
    private int mWindowWidth;
    //    private GameSound mGameSound;
    public int rollPositionID;  // 滚动回调的 postion

    private Random mRandom;
    private boolean isDownHook;  //下勾动画是否可以下
    private boolean isFire;  //是否在请求接口s
    private boolean isStopView;  //是否在请求接口的情况下点击小怪判断小怪有没有过屏幕边界

    private HashMap<Integer, GameRoolBean> mGameBaseMap = new HashMap<>();

    private ArrayList<Integer> mapId = new ArrayList<>();
    private ArrayList<Integer> betList = new ArrayList<>();
    private float fireAngle;  //旋转的角度
    private View mRollItemView;  // 点击小怪的view
    private AnimationDrawable mIv_gun_light_anim;
    private int[] mGiftBGData = new int[]{
            R.drawable.game_dfj_0_8,
            R.drawable.game_dfj_1_2,
            R.drawable.game_dfj_2,
            R.drawable.game_dfj_4,
            R.drawable.game_dfj_6,
            R.drawable.game_dfj_10,
            R.drawable.game_dfj_25,
            R.drawable.game_dfj_50,

    };
    private int[] mGiftBGData_border = new int[]{// 头像数据<点击闪动效果>
            R.drawable.game_dfj_0_8_nor,
            R.drawable.game_dfj_1_2_nor,
            R.drawable.game_dfj_2_nor,
            R.drawable.game_dfj_4_nor,
            R.drawable.game_dfj_6_nor,
            R.drawable.game_dfj_10_nor,
            R.drawable.game_dfj_25_nor,
            R.drawable.game_dfj_50_nor,
    };

    int[] garlandResId = {
            R.drawable.game_dfj_fire_bao_zha_1,
            R.drawable.game_dfj_fire_bao_zha_2,
            R.drawable.game_dfj_fire_bao_zha_3,
            R.drawable.game_dfj_fire_bao_zha_4,
            R.drawable.game_dfj_fire_bao_zha_5,
            R.drawable.game_dfj_fire_bao_zha_6,
            R.drawable.game_dfj_fire_bao_zha_7,
            R.drawable.game_dfj_fire_bao_zha_8,
            R.drawable.game_dfj_fire_bao_zha_9,
            R.drawable.game_dfj_fire_bao_zha_10,
            R.drawable.game_dfj_fire_bao_zha_11,
    };
    private AnimImageUtil mItemWinAnim;  //赢钱的动画
    private ImageView view_fire; // 背景
    private View mView;
    private ObjectAnimator mBackwardsAnimator;  // 向后的动画
    private ObjectAnimator mFireViewRotation;

    public DFJLayout(Context context, boolean isPublish) {
        this(context, null, isPublish);

    }

    public DFJLayout(Context context, AttributeSet attrs, boolean isPublish) {
        super(context, attrs);
        this.isPublish = isPublish;
//        mPresenter = new Game2Preserter(this);
        this.mContext = context;
        mWindowHeight = GameUtil.getWindowHeight(mContext);
        mWindowWidth = GameUtil.getWindowWidth(mContext);
//        if (!isPublish) {
//            mGameSound = new GameSound(GameObserver.getAppObserver().getApp());
//            mGameSound.soundPool();
//        }

//        mPresenter = new Game2Preserter(this);
        betList.add(bet10);
        betList.add(bet100);
        betList.add(bet1000);
        betList.add(bet5000);
        //初始化状态抓子上去的动画
        mRandom = new Random();
        mView = LayoutInflater.from(context).inflate(R.layout.game_layout_main_dfj, null);
        this.addView(mView);
        initView(mView);
        //初始化 bet button
        mMy_diamonds = getMoney();

        GameUIUtils.initnotityDFJUI(mMy_diamonds, currentBet, iv_bet10, iv_bet100, iv_bet1000);

        initLightParams();
        if (!isPublish) {
//            initBuMusic();
        }
    }

    /**
     * chu初始化声音
     */
    private void initBuMusic() {
        if (!isOpneGameBgSound) {
//            ((GameSoundBgtext) GameSoundBgtext.getInstance()).playDfjBg(mContext);
        }
    }

    public int getTotalDuration(AnimationDrawable animationDrawable) {

        int iDuration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            iDuration += animationDrawable.getDuration(i);
        }

        return iDuration;
    }

    @Override
    public void showFireResult(boolean hit, final int my_diamonds) {
        GameRoolBean gameRoolBean = mGameBaseMap.get(rollPositionID);
        final int winMoney = (int) (gameRoolBean.radix * currentBet);
        setMoneyTextView(my_diamonds, 0);
        //测试
//        hit =false;
        if (hit) {
            final ImageView rollItemView = (ImageView) mRollItemView;
            rollItemView.setClickable(false);

            mItemWinAnim = new AnimImageUtil(rollItemView, garlandResId, 50, 1);
            mItemWinAnim.setOnanimationListener(new AnimImageUtil.animationListener() {
                @Override
                public void start() {
                }

                @Override
                public void stop() {
                    rollItemView.setClickable(true);
                    if (rollItemView == null) {
                        return;
                    }
                    showWinMoneyAnim(winMoney, my_diamonds);
                }
            });
        } else {
            isStopView = false;
            isFire = false;
        }

    }

    private void showWinMoneyAnim(int money, final int my_diamonds) {

        GameAnimUtil.startWinTextAnim(DFJLayout.this, mRollItemView, money, isStopView, mTypeFace);
        if (!isStopView) {
            mRollItemView.setVisibility(GONE);
        }
        GameAnimUtil.startWinAnim(DFJLayout.this, mRollItemView, iv_money, isStopView);
        if (!isPublish) {
//            mGameSound.playSound(SOUND_WIN_GOLD, 0);
            mGameSound.playSound(GameSoundZww.SOUND_WIN_GOLD, 0);
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.startWinAnim(DFJLayout.this, mRollItemView, iv_money, isStopView);
            }
        }, 100);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.startWinAnim(DFJLayout.this, mRollItemView, iv_money, isStopView);
            }

        }, 200);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.startWinAnim(DFJLayout.this, mRollItemView, iv_money, isStopView);
                isStopView = false;
                isFire = false;
                setMoney(my_diamonds);
            }
        }, 300);
    }

    @Override
    public void showFireError() {
        isFire = false;
        isStopView = false;
    }

    @Override
    public void setMoneyTextView(int diamonds, long delayed) {
        super.setMoneyTextView(diamonds, delayed);
        if (delayed == 0) {// 点击时候的
            if (currentBet > diamonds) {
                if (GameAnimUtil.BET_100 < diamonds && diamonds < GameAnimUtil.BET_1000) {
                    onClick(iv_bet100);
                } else if (diamonds <= GameAnimUtil.BET_100 && diamonds > GameAnimUtil.BET_10) {
                    onClick(iv_bet10);
                }
            } else {
                GameUIUtils.initnotityDFJUI(diamonds, currentBet, iv_bet10, iv_bet100, iv_bet1000);
            }
        } else { // 充值的回来刷新
            if (currentBet == GameAnimUtil.BET_1000) {
                onClick(iv_bet1000);
            } else if (currentBet == GameAnimUtil.BET_100) {
                onClick(iv_bet100);
            } else if (currentBet == GameAnimUtil.BET_10) {
                onClick(iv_bet10);
            }
        }

    }

    //
    private void downHook(boolean isFireItem) {
        isFire = true;
        int my_diamonds = getMoney();
        setMoney(my_diamonds - currentBet);
        mGameSound.playSound(GameSoundZww.GAME_DFJ_FIRE, 0);
        mPresenter.fire(
                isFireItem,
                isFireItem ? rollPositionID : -1,
                currentBet);

    }

    @Override
    public void startGamestar_warsData(List<XiaZhuPush.GameBaen> star_wars) {
        int diamonds = getMoney();
        setMoney(diamonds);
        ininData(star_wars);
        mRool_view_top.startAnim();
        mRool_view_buttom.startAnim();
    }

    @Override
    public int getGameType() {
        return GameC.Game.GAME_TYPE_DFJ;
    }

    private void ininData(List<XiaZhuPush.GameBaen> star_wars) {
        if (star_wars == null) {
            return;
        }
        for (int i = 0; i < star_wars.size(); i++) {
            XiaZhuPush.GameBaen gameBaen = star_wars.get(i);
            GameRoolBean gameRoolBean = new GameRoolBean();
            gameRoolBean.res = mGiftBGData[gameBaen.getId() - 1];
            gameRoolBean.res_border = mGiftBGData_border[gameBaen.getId() - 1];
            gameRoolBean.id = gameBaen.getId();
            gameRoolBean.radix = gameBaen.getRadix();
            mGameBaseMap.put(gameBaen.getId(), gameRoolBean);
            mapId.add(gameBaen.getId());
        }
        mRool_view_buttom.setmData(mapId, mGameBaseMap);
        mRool_view_top.setmData(mapId, mGameBaseMap);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.chongzhi) {
            EventBus.getDefault().post(new GoPayListActivityEvent());

        } else if (i == R.id.bet10) {
            if (!isinitMoneyBet && bet10 > mMy_diamonds) {
                GameUIUtils.showCheckMoney();
                return;
            }
            iv_gun_light.setImageResource(R.drawable.game_dfj_fire_light);
            mIv_gun_light_anim = (AnimationDrawable) iv_gun_light.getDrawable();
            mIv_gun_light_anim.stop();
            currentBet = bet10;
            GameUIUtils.notityDFJUI(this, mMy_diamonds, currentBet, rl_game_bg, iv_gun, iv_bet10, iv_bet100, iv_bet1000, view_fire);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rl_gun_all.setPivotX(rl_gun_all.getMeasuredWidth() / 2);
                    rl_gun_all.setPivotY(rl_gun_all.getMeasuredHeight() - 15);
                }
            }, 20);

        } else if (i == R.id.bet100) {
            if (!isinitMoneyBet && bet100 > mMy_diamonds) {
                GameUIUtils.showCheckMoney();
                return;
            }

            iv_gun_light.setImageResource(R.drawable.game_dfj_fire_light);
            mIv_gun_light_anim = (AnimationDrawable) iv_gun_light.getDrawable();
            mIv_gun_light_anim.stop();
            currentBet = bet100;
            GameUIUtils.notityDFJUI(this, mMy_diamonds, currentBet, rl_game_bg, iv_gun, iv_bet10, iv_bet100, iv_bet1000, view_fire);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rl_gun_all.setPivotX(rl_gun_all.getMeasuredWidth() / 2);
                    rl_gun_all.setPivotY(rl_gun_all.getMeasuredHeight() - 15);
                }
            }, 20);

        } else if (i == R.id.bet1000) {
            if (!isinitMoneyBet && bet1000 > mMy_diamonds) {
                GameUIUtils.showCheckMoney();
                return;
            }

            iv_gun_light.setImageResource(R.drawable.game_dfj_fire_light_adv);
            mIv_gun_light_anim = (AnimationDrawable) iv_gun_light.getDrawable();
            mIv_gun_light_anim.stop();
            currentBet = bet1000;
            GameUIUtils.notityDFJUI(this, mMy_diamonds, currentBet, rl_game_bg, iv_gun, iv_bet10, iv_bet100, iv_bet1000, view_fire);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rl_gun_all.setPivotX(rl_gun_all.getMeasuredWidth() / 2);
                    rl_gun_all.setPivotY(rl_gun_all.getMeasuredHeight() - 15);
                }
            }, 20);
        }
        initLightParams();
        isinitMoneyBet = false;
    }

    private void initLightParams() {
        ViewGroup.LayoutParams layoutParams10 = iv_gun_light.getLayoutParams();
//        layoutParams10.width = GameUtil.dip2px(mContext, 50);
//        layoutParams10.height = GameUtil.dip2px(mContext, 50);
        layoutParams10.height = (int) GameUtil.getDimension(R.dimen.game_dfj_light_height);
        layoutParams10.width = (int) GameUtil.getDimension(R.dimen.game_dfj_light_height);
        iv_gun_light.setLayoutParams(layoutParams10);
        invalidate();
    }


    private void initView(View view) {

        mRool_view_top = (GameDFJRollView) view.findViewById(R.id.rool_view_top);

        mRool_view_buttom = (GameDFJRollView) view.findViewById(R.id.rool_view_bottom);
        rl_game_bg = (RelativeLayout) view.findViewById(R.id.rl_game_bg);
        iv_gun = (ImageView) view.findViewById(R.id.iv_gun);
        iv_gun_light = (ImageView) view.findViewById(R.id.iv_gun_light);

        rl_gun_all = (RelativeLayout) view.findViewById(R.id.rl_gun_all);

        view.findViewById(R.id.chongzhi).setOnClickListener(this);
        view_fire = (ImageView) view.findViewById(R.id.view_fire);
        toolbar = view.findViewById(R.id.toolbar);
        tv_money = (TextView) toolbar.findViewById(R.id.tv_money);
        iv_money = (ImageView) toolbar.findViewById(R.id.money);
        iv_bet10 = (ImageView) toolbar.findViewById(R.id.bet10);
        iv_bet100 = (ImageView) toolbar.findViewById(R.id.bet100);
        iv_bet1000 = (ImageView) toolbar.findViewById(R.id.bet1000);
        iv_bet10.setOnClickListener(this);
        iv_bet100.setOnClickListener(this);
        iv_bet1000.setOnClickListener(this);

        initRoolViewListener();
        //点击背景
        initBgEvent(view);

        iv_gun_light.setImageResource(R.drawable.game_dfj_fire_light);
        mIv_gun_light_anim = (AnimationDrawable) iv_gun_light.getDrawable();
        if (mIv_gun_light_anim != null && mIv_gun_light_anim.isRunning()) {
            mIv_gun_light_anim.stop();

        }
//        toolbar.setOnClickListener(this);
    }

    private void initBgEvent(View view) {
        view_fire.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case KeyEvent.ACTION_UP:
                        float motionX = motionEvent.getX();
                        float motionY = motionEvent.getY();
                        mMy_diamonds = getMoney();
                        if (!GameUIUtils.checkMoney(mMy_diamonds)) {
                            isFire = false;
                            isStopView = false;
                            return true;
                        }
                        startFireAngle(motionX, motionY);
                        if (isFire) {
                            return true;
                        }
                        downHook(false);
                        break;
                }
                return true;
            }
        });
    }

    private void initRoolViewListener() {
        mRool_view_top.setAninListener(new GameDFJRollView.AninListener() {

            @Override
            public void onClickItemView(int id, View view) {
                mMy_diamonds = getMoney();
                if (!GameUIUtils.checkMoney(mMy_diamonds)) {
                    isFire = false;
                    isStopView = false;
                    return;
                }
                startFireAngle(view);
                if (isFire) {
                    return;
                }
                DFJLayout.this.mRollItemView = view;
                rollPositionID = id;
                downHook(true);

            }

            @Override
            public void onAnimationRepeat(View view) {
                if (mRollItemView != null) {
                    if (isFire) {  // 在请求网络
                        if (DFJLayout.this.mRollItemView.equals(view)) {//对象相同
                            isStopView = true;
                        }
                    }
                }
            }

        }).initView(R.layout.game_layout_dfj_item);
        //点击小怪
        mRool_view_buttom.setAninListener(new GameDFJRollView.AninListener() {

            @Override
            public void onClickItemView(int id, View view) {


                mMy_diamonds = getMoney();
                if (!GameUIUtils.checkMoney(mMy_diamonds)) {
                    isFire = false;
                    isStopView = false;
                    return;
                }
                startFireAngle(view);
                if (isFire) {
                    return;
                }
                DFJLayout.this.mRollItemView = view;
                rollPositionID = id;
                downHook(true);

            }

            @Override
            public void onAnimationRepeat(View view) {
                if (mRollItemView != null) {
                    if (isFire) {
                        if (DFJLayout.this.mRollItemView.equals(view)) {
                            isStopView = true;
                        }
                    }
                }
            }
        }).initView(R.layout.game_layout_dfj_item);

    }


    //开始旋转炮
    private void startFireAngle(float motionX, float motionY) {
        mIv_gun_light_anim.stop();
        mIv_gun_light_anim.start();
        rl_gun_all.setPivotX(rl_gun_all.getMeasuredWidth() / 2);
        rl_gun_all.setPivotY(rl_gun_all.getMeasuredHeight() - 15);

        float fireAngleNew = (float) getFireAngle(motionX, motionY);
        mFireViewRotation = ObjectAnimator.ofFloat(rl_gun_all, "rotation", fireAngle, fireAngleNew);

        mFireViewRotation.setDuration(10);
        mFireViewRotation.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mBackwardsAnimator == null) {
                    mBackwardsAnimator = ObjectAnimator.ofFloat(rl_gun_all, "translationY", 0, 10, 0);
                    mBackwardsAnimator.setDuration(200);
                }
                mBackwardsAnimator.start();
            }
        });
        mFireViewRotation.start();
        DFJLayout.this.fireAngle = fireAngleNew;

    }

    //开始旋转炮
    private void startFireAngle(View view) {
        mIv_gun_light_anim.stop();
        mIv_gun_light_anim.start();
        float fireAngleNew = (float) getFireAngle(view);
        mFireViewRotation = ObjectAnimator.ofFloat(rl_gun_all, "rotation", fireAngle, fireAngleNew);
        mFireViewRotation.setDuration(10);
        mFireViewRotation.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mBackwardsAnimator == null) {
                    mBackwardsAnimator = ObjectAnimator.ofFloat(rl_gun_all, "translationY", 0, 10, 0);
                    mBackwardsAnimator.setDuration(200);
                }
                mBackwardsAnimator.start();
            }
        });
        mFireViewRotation.start();
        DFJLayout.this.fireAngle = fireAngleNew;
    }

    public double getFireAngle(View view) {
        rl_gun_all.setPivotX(rl_gun_all.getMeasuredWidth() / 2);
        rl_gun_all.setPivotY(rl_gun_all.getMeasuredHeight() - 15);
        int[] locationOnScreen = GameUtil.getLocationOnScreen(view);
        int viewPaddingLeft = view.getPaddingLeft();
        int pxPaddingLeft = GameUtil.px2dip(mContext, viewPaddingLeft);
        int width = view.getWidth();
//        int viewWidth = width - pxPaddingLeft;
        int height = view.getHeight();
        int ViewX = locationOnScreen[0] + width / 2 + pxPaddingLeft;
        int ViewY = locationOnScreen[1] + height / 2;
        int itemViewY = mWindowHeight - ViewY;
        int itemViewX = mWindowWidth / 2 - ViewX;
        itemViewY = Math.abs(itemViewY);
        itemViewX = Math.abs(itemViewX);
        int itemViewZ = itemViewY * itemViewY + itemViewX * itemViewX;
        double sqrtZ = Math.sqrt(itemViewZ);
        double fireAngle = itemViewX / sqrtZ;
        fireAngle = fireAngle * 60;
        if (mWindowWidth / 2 > ViewX) {
            return -fireAngle;
        } else {
            return fireAngle;
        }
    }


    public double getFireAngle(float motionX, float motionY) {

        rl_gun_all.setPivotX(rl_gun_all.getMeasuredWidth() / 2);
        rl_gun_all.setPivotY(rl_gun_all.getMeasuredHeight() - 15);
        int ViewX = (int) (motionX);
        int ViewY = (int) (motionY);
        int itemViewY = mWindowHeight - ViewY;
        int itemViewX = mWindowWidth / 2 - ViewX;
        itemViewY = Math.abs(itemViewY);
        itemViewX = Math.abs(itemViewX);
        int itemViewZ = itemViewY * itemViewY + itemViewX * itemViewX;
        double sqrtZ = Math.sqrt(itemViewZ);
        double fireAngle = itemViewX / sqrtZ;
        fireAngle = fireAngle * 180;
        if (mWindowWidth / 2 > ViewX) {
            return -fireAngle;
        } else {
            return fireAngle;
        }
    }

    @Override
    protected void onDetachedFromWindow() {

        if (mBackwardsAnimator != null) {
            mBackwardsAnimator.removeAllListeners();
            mBackwardsAnimator.removeAllUpdateListeners();
            mBackwardsAnimator.cancel();
        }
        if (mFireViewRotation != null) {
            mFireViewRotation.removeAllListeners();
            mFireViewRotation.removeAllUpdateListeners();
            mFireViewRotation.cancel();
        }

        if (mIv_gun_light_anim != null && mIv_gun_light_anim.isRunning()) {
            GameUtil.closeAnimationDrawable(mIv_gun_light_anim);
        }
        if (mItemWinAnim != null) {
            mItemWinAnim.DestroyView();
        }

        if (!isPublish) {
            if (GameSoundBg.getNewInstance() != null) {
                GameSoundBg.getInstance().stopSound();
            }
        }
        if (mView != null) {
            this.removeView(mView);
            mView = null;
        }
        super.onDetachedFromWindow();
    }


}
