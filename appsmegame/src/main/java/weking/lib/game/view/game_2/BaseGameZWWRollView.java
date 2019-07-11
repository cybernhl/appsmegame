package weking.lib.game.view.game_2;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import weking.lib.game.R;
import weking.lib.game.bean.GiftListBean;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.presenter.AnimatorFactory;
import weking.lib.game.utils.GameAnimUtil;
import weking.lib.game.utils.GameUIUtils;
import weking.lib.game.utils.GameUtil;
import weking.lib.utils.LogUtils;

import static android.content.ContentValues.TAG;
import static weking.lib.game.utils.GameUtil.getDimension;


/**
 * 创建时间 2017/6/15.
 * 创建人 frs
 * 功能描述  旋转动画
 */
public abstract class BaseGameZWWRollView extends RelativeLayout {


    private long mRollItemScrollWidthLeft;   //可以抓住的点Left
    private long mRollItemScrollWidthRight;  //可以抓住的点Right


    private int animDuration = 13000;//执行动画的时间
    private int animOnceNumber = 4;//动画循环一次的个数
    private int boundHeight = 50;    //跳动的高度

    private int itemStatePosition = 5;//动画循环一次后开始取得   数据  位置
    private int itemViewStatePosition = 0;//动画循环一次后开始取得  视图   位置
    private int itemIntervalSize = 30;//抓空的大小
    // 测试　失败
//    private int itemIntervalSize = 150;//抓空的大小
    // 测试  成功
//    private int itemIntervalSize = 0;//抓空的大小

    private int startAninIndex = 0;    //开启动画的标记
    private Handler mHandler = new Handler();
    private ArrayList<ObjectAnimator> rollAnimList = new ArrayList<>();
    private ArrayList<ObjectAnimator> mAnimList = new ArrayList<>();
    //    private HashMap<Integer, GiftListBean> mData = new HashMap<>();  //礼物数据
    private ArrayList<GameRoolBean> beanList = new ArrayList<>(); //旋转对象保存
    private Context mContext;
    private AninListener mAninListener;
    private boolean isBoundView = false;//是否上下移动
    private boolean isListenerAddUpdate = false;//是否监听滚动位子
    private boolean isReverseRoll = false;//是否反向转动
    private boolean isInitAnim = false;//是否初始化动画


    private int mWindowWidht;
    private int mWindowWidhtGo2;
    private int[] mGiftBGData;// 头像数据
    private ArrayList<Integer> mapIndex = new ArrayList<>();
    private int mItmeBG = -1;  //背景
    private float mScaleX;
    private float mScaleY;
    private boolean mFierState;  // true 在请求接口

    public BaseGameZWWRollView(Context context) {
        this(context, null);
    }

    public BaseGameZWWRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mWindowWidht = getWindowWidth();
        this.mWindowWidhtGo2 = mWindowWidht / 2;
//        itemWidth = (int) Util.getDimension(R.dimen.game_zww_item_iv_width);
//        itemHeight = (int) Util.getDimension(R.dimen.game_zww_item_iv_height);
    }


//    Runnable animRun = new Runnable() {
//        @Override
//        public void run() {
//            if (!isInitAnim) {
//                LogUtils.e(TAG, "no init GameRollView");
//                return;
//            }
//            if (startAninIndex >= animOnceNumber) {
//                mHandler.removeCallbacks(animRun);
//                return;
//            }
//            LogUtils.e(TAG, "run111:   " + startAninIndex);
//            rollAnimList.get(startAninIndex).start();
//            mHandler.postDelayed(animRun, animDuration / animOnceNumber);
//            startAninIndex++;
//        }
//    };

    public void startAnim() {
//        mHandler.postDelayed(animRun, 0);
        this.invalidate();
    }


    public void initView(int resource) {
        this.isInitAnim = true;
        for (int i = 0; i < animOnceNumber; i++) {
            ininviewAnim(i, resource);
        }
    }

    //初始化动画
    private void initAnim(final View view, final int position, final float boundWidth) {
        final float ivWidht = getDimension(R.dimen.game_zww_item_iv_width);
        int toWindowWidgt = (int) ((mWindowWidht + ivWidht) * (position + 1) / animOnceNumber);
        view.setVisibility(VISIBLE);
        float viewStartWidht = (mWindowWidht - toWindowWidgt);  //开始的位置
//        final long vTiem = 2000;
        final long vTiem = animDuration / (animOnceNumber + 1);
        float AnimPositionTiem = animDuration - (viewStartWidht * animDuration / (mWindowWidht + ivWidht)) - vTiem;
        ObjectAnimator animator = null;
        if (isReverseRoll) {
            animator = ObjectAnimator.ofFloat(view, "translationX", (int) ((mWindowWidht + ivWidht) * (position) / animOnceNumber), -ivWidht);
        } else {
            animator = ObjectAnimator.ofFloat(view, "translationX", viewStartWidht, mWindowWidht);
        }
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                rollAnimList.get(position).start();
//                    }
//                }, vTiem);
            }
        });
        if (mRollItemScrollWidthRight == 0 || mRollItemScrollWidthLeft == 0) {
        }
        final long itemRight = mWindowWidhtGo2 - mRollItemScrollWidthRight;
        final long itemLeft = mWindowWidhtGo2 - mRollItemScrollWidthLeft;
        final long itemNoRight = itemRight - (itemIntervalSize);
        final long itemNoLeft = itemLeft - (itemIntervalSize);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                       @Override
                                       public void onAnimationUpdate(ValueAnimator animation) {
                                           if (mAninListener != null) {
                                               Float curValueFloat = (Float) animation.getAnimatedValue();
                                               int curValue = curValueFloat.intValue();
                                               if (curValue > (itemRight) && curValue < (itemLeft)) {
                                                   if (curValue > (itemNoRight) && curValue < (itemNoLeft)) {
                                                       mAninListener.onAnimationUpdateSucceed(curValue, position, view);
                                                   } else {
                                                       mAninListener.onAnimationUpdateError(curValue, position, view);
                                                   }
                                               }
                                           }
                                       }
                                   }
        );
        animator.setDuration((long) AnimPositionTiem);
        animator.start();
        mAnimList.add(animator);
    }


    private synchronized void ininviewAnim(final int position, int resource) {
        int isoneGoTo = 0;
//        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(itemWidth, itemHeight);
        final GameRoolBean bean = new GameRoolBean();
        View view = null;
        try {
            view = LayoutInflater.from(this.mContext).inflate(resource, null);
            findId(view, bean);
            if (this.mItmeBG != -1) {
                bean.HEAD.setColorFilter(this.mItmeBG);
            }
        } catch (Exception e) {
            new Exception("检查你的  resource  ");
        }
        view.setVisibility(GONE);
        float ivWidht = GameUtil.getDimension(R.dimen.game_zww_item_iv_width);

        ObjectAnimator animator = null;
        if (isReverseRoll) {
            animator = ObjectAnimator.ofFloat(view, "translationX", mWindowWidht, -ivWidht);
        } else {
            animator = ObjectAnimator.ofFloat(view, "translationX", -ivWidht, mWindowWidht);
        }
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(animDuration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        final int[] tag = {0};
        final View finalView1 = view;

        if (mScaleX != 0) {
            AnimatorSet animatorSet = new AnimatorSet();//组合动画
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, mScaleX);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, mScaleY);
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setDuration(0);
            animatorSet.start();
        }

        animator.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationStart(Animator animation) {
                finalView1.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

//                if (mData == null) {
//                    return;
//                }

                if (itemStatePosition == mapIndex.size()) {
                    itemStatePosition = 0;
                }
                if (itemViewStatePosition == animOnceNumber) {
                    itemViewStatePosition = 0;
                }

                GameRoolBean bean = beanList.get(itemViewStatePosition);
                GiftListBean giftListBean = GameObserver.getAppObserver().getGiftBean(mapIndex.get(itemStatePosition));
                if (giftListBean == null) {
                    return;
                }
                // 显示条目
                bean.VIEW.setVisibility(VISIBLE);
                bean.TV.setText(giftListBean.price + "");
                bean.HEAD.setImageResource(mGiftBGData[itemStatePosition]);
                GameObserver.getAppObserver().loaderImage(bean.GIFT, giftListBean.pic_url);
                bean.position = itemStatePosition;
//                notifyitemData(itemStatePosition);
                itemStatePosition++;
                itemViewStatePosition++;
//                if (tag[0] == 0) {
//                    tag[0]++;
//                    iv_head.setImageResource(R.mipmap.ic_launcher);
//                } else {
//                    tag[0]--;
//                    iv_head.setImageResource(R.mipmap.pic_bear_b);
//                }

            }
        });

        //监听动画位置
        if (isListenerAddUpdate) {
            if (mRollItemScrollWidthRight == 0 || mRollItemScrollWidthLeft == 0) {
//                Log.e(TAG, " init Rollview Erorr   mRollItemScrollWidthRight == 0 ||  mRollItemScrollWidthLeft == 0 ");
            }
            final long itemRight = mWindowWidhtGo2 - mRollItemScrollWidthRight;
            final long itemLeft = mWindowWidhtGo2 - mRollItemScrollWidthLeft;
            final long itemNoRight = itemRight - (itemIntervalSize);
            final long itemNoLeft = itemLeft - (itemIntervalSize);

            final View finalView = view;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                float view1Y;

                boolean tag = true;

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
//                    PointF pointF = (PointF) animation.getAnimatedValue();
//                    target.setX();
//                    target.setY(pointF.y);
                    if (mFierState) {
                        return;
                    }
                    if (mAninListener != null) {
                        Float curValueFloat = (Float) animation.getAnimatedValue();
                        int curValue = curValueFloat.intValue();
//                        Log.d(TAG, "onAnimationUpdate111  : " + curValue + "        " + GameRollView.this.toString());
                        if (curValue > (itemRight) && curValue < (itemLeft)) {
                            if (curValue > (itemNoRight) && curValue < (itemNoLeft)) {
                                mAninListener.onAnimationUpdateSucceed(curValue, bean.position, bean.VIEW);
                            } else {
                                mAninListener.onAnimationUpdateError(curValue, bean.position, bean.VIEW);
                            }
//                            String text = (String) bean.TV.getText();
//                        if ((mWindowWidhtGo2 - mRollItemScrollWidthRight) < curValue && curValue > (mWindowWidhtGo2 - mRollItemScrollWidthLeft)) {
//                            Log.d(TAG, "onAnimationUpdate222: curValue   " + curValue + "   bean.position   " + bean.position);

                        }
                    }
//                    if (isBoundView) {
//                        if (tag) {
//                            view1Y += 2;
//                        } else {
//                            view1Y -= 2;
//                        }
//                        if (view1Y > boundWidth) {
//                            tag = false;
//
//                        } else if (view1Y < 0) {
//                            tag = true;
//                        }
//                        //设置高度
//                        finalView.setY(view1Y);
//                    }
                }
            });
        }
        this.rollAnimList.add(animator);

        bean.VIEW = view;
        beanList.add(bean);

        this.addView(view);


    }

    protected abstract void findId(View view, GameRoolBean bean);


    public int getWindowWidth() {

        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public void destroyView() {
//        mHandler.removeCallbacks(animRun);
    }

    public BaseGameZWWRollView setAninListener(AninListener l) {
        this.mAninListener = l;
        return this;
    }

    //不能连点操作  必须在初始化UI之后
    public void setmData(ArrayList<Integer> mapIndex) {
        if (mapIndex == null || mapIndex.size() == 0) {
            new Exception("不能连点操作  必须在初始化UI之后");
            return;
        }
        if (mapIndex == null || mapIndex.size() < beanList.size()) {
            LogUtils.e(TAG, "setmData: 请检查你的数据集合 ==  beanList.size()  ??");
            return;
        }

        this.mapIndex.clear();
        this.mapIndex.addAll(mapIndex);

        notifyAllData();
    }

    public void notifyData(ArrayList<Integer> mapIndex, int bet) {
        if (mapIndex == null || mapIndex.size() == 0) {
            new Exception("不能连点操作  必须在初始化UI之后");
            return;
        }
        if (mapIndex == null || mapIndex.size() < beanList.size()) {
            LogUtils.e(TAG, "setmData: 请检查你的数据集合 ==  beanList.size()  ??");
            return;
        }

        this.mapIndex.clear();
        this.mapIndex.addAll(mapIndex);
        for (int i = 0; i < beanList.size(); i++) {
            GameRoolBean bean = beanList.get(i);
            int id = mapIndex.get(bean.position);
            GiftListBean giftListBean = GameObserver.getAppObserver().getGiftBean(id);

//            GiftListBean giftListBean = GiftListMemory.get(id);
            GameUIUtils.notityItemBgBetUI(bet, bean.GIFT);

            if (giftListBean != null) {
                GameObserver.getAppObserver().loaderImage(bean.GIFT, giftListBean.pic_url);
                bean.TV.setText(giftListBean.price + "");
            }
            invalidate();
        }

    }


    //刷新礼物数据  <全部一次的>
    private void notifyAllData() {
        for (int i = 0; i < beanList.size(); i++) {
            notifyitemData(i);
            //启动动画
            GameRoolBean gameRoolBean = beanList.get(i);
            initAnim(gameRoolBean.VIEW, i, gameRoolBean.boundWidth);
        }
    }

    //刷新一条礼物数据
    private void notifyitemData(int i) {
        GameRoolBean bean = beanList.get(i);
        if (bean != null) {
            bean.position = i;
            int integer = mapIndex.get(i);
            GiftListBean giftListBean = GameObserver.getAppObserver().getGiftBean(integer);
            bean.TV.setText(giftListBean.price + "");
            bean.HEAD.setImageResource(mGiftBGData[i]);
            GameUIUtils.notityItemBgBetUI(GameAnimUtil.BET_10, bean.GIFT);
            GameObserver.getAppObserver().loaderImage(bean.GIFT, giftListBean.pic_url);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        if (mAnimList != null && rollAnimList != null) {
            mAnimList.addAll(rollAnimList);
        }
        if (mAnimList != null && mAnimList.size() != 0) {
            for (ObjectAnimator anin : mAnimList) {
                if (anin != null) {
                    anin.cancel();
                    anin.removeAllUpdateListeners();
                    anin.removeAllListeners();
                }
            }
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDetachedFromWindow();
    }


    public void clichView() {

//        ViewList.get(2).bringToFront();
//        TranslateAnimation animationplane = new TranslateAnimation(0, 0, 0, -1110);
//        animationplane.setDuration(500);
//        ViewList.get(2).setAnimation(animationplane);
//        animationplane.start();
//        ViewList.get(2).startAnimation(animationplane);
////        ObjectAnimator animator = ObjectAnimator.ofFloat(ViewList.get(2), "translationY", 0, 50);
////        animator.setDuration(1000);
////        animator.start();
//        invalidate();
    }

    public BaseGameZWWRollView setGiftBGData(int[] giftBGData) {
        mGiftBGData = giftBGData;
        return this;
    }


    // 隐藏条目
    public void goneItmeView() {
        beanList.get(itemViewStatePosition).VIEW.setVisibility(GONE);
    }

    public BaseGameZWWRollView setItmeBG(int itmeBG) {

        mItmeBG = itmeBG;
        return this;
    }

    public void setFierState(boolean fierState) {

        mFierState = fierState;
    }


    public interface AninListener {
        void onAnimationUpdateSucceed(int curValue, int position, View view);

        void onAnimationUpdateError(int curValue, int position, View view);

    }


    public BaseGameZWWRollView setBoundView(boolean boundView) {
        isBoundView = boundView;
        return this;
    }

    public BaseGameZWWRollView setListenerAddUpdate(boolean listenerAddUpdate) {
        isListenerAddUpdate = listenerAddUpdate;
        return this;
    }


    public BaseGameZWWRollView setReverseRoll(boolean reverseRoll) {
        isReverseRoll = reverseRoll;
        return this;
    }

    public BaseGameZWWRollView setRollItemScrollSize(long rollItemScrollWidthRight, long rollItemScrollWidthLeft) {
        mRollItemScrollWidthRight = rollItemScrollWidthRight;
        mRollItemScrollWidthLeft = rollItemScrollWidthLeft;
        return this;
    }

    //旋转item
    public BaseGameZWWRollView setItemScale(float scaleX, float scaleY) {
        mScaleX = scaleX;
        mScaleY = scaleY;
        return this;
    }


    public BaseGameZWWRollView setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
        return this;
    }

    public BaseGameZWWRollView setAnimOnceNumber(int animOnceNumber) {
        this.animOnceNumber = animOnceNumber;
        return this;
    }


    public BaseGameZWWRollView setBoundHeight(int boundHeight) {
        this.boundHeight = boundHeight;
        return this;
    }

    public ArrayList<GameRoolBean> getBeanList() {
        return beanList;
    }
}
