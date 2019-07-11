package weking.lib.game.view.game_2;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import weking.lib.game.R;
import weking.lib.game.bean.GiftListBean;
import weking.lib.game.presenter.AnimatorFactory;
import weking.lib.game.utils.GameUtil;
import weking.lib.utils.LogUtils;

import static android.animation.ObjectAnimator.ofFloat;


/**
 * 创建时间 2017/6/15.
 * 创建人 frs
 * 功能描述  旋转动画
 */
public class GameDFJRollView extends RelativeLayout {
    private static final String TAG = "GameDFJInfolView";


    private int animDuration = 12000;//执行动画的时间
    private int animOnceNumber = 4;//动画循环一次的个数
    private int boundHeight = 50;    //跳动的高度
    private int randomHeight = 25;    //跳动的高度

    private int itemViewStatePosition = 0;//动画循环一次后开始取得  视图   位置
    private int startAninIndex = 0;    //开启动画的标记
    private Handler mHandler = new Handler();
    private ArrayList<ObjectAnimator> animList = new ArrayList<>();
    private ArrayList<ObjectAnimator> rollAnimList = new ArrayList<>();
    private HashMap<Integer, GiftListBean> mData = new HashMap<>();  //礼物数据
    private ArrayList<GameRoolBean> beanList = new ArrayList<>(); //旋转对象保存
    private Context mContext;
    private AninListener mAninListener;
    private boolean isInitAnim = false;//是否初始化动画
    HashMap<Integer, GameRoolBean> mGameBaseMap = new HashMap<>();

    private int mWindowWidht;
    private int mWindowWidhtGo2;


    private ArrayList<Integer> mapIndex = new ArrayList<>();
    private int mItmeBG = -1;  //背景
    private Random mRandom;
    private ObjectAnimator mItmeDouAnim;

    public GameDFJRollView(Context context) {
        super(context, null);
    }

    public GameDFJRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mWindowWidht = getWindowWidth();
        this.mWindowWidhtGo2 = mWindowWidht / 2;
        mRandom = new Random();

    }


    Runnable animRun = new Runnable() {
        @Override
        public void run() {
            if (!isInitAnim) {
                LogUtils.e(TAG, "no init GameRollView");
                return;
            }
            if (startAninIndex >= animOnceNumber) {
                mHandler.removeCallbacks(animRun);
                return;
            }
            LogUtils.e(TAG, "run111:   " + startAninIndex);
            rollAnimList.get(startAninIndex).start();

            mHandler.postDelayed(animRun, animDuration / animOnceNumber);
            startAninIndex++;
        }
    };

    public void startAnim() {
        this.invalidate();
    }


    public void initView(int resource) {
        this.isInitAnim = true;
        for (int i = 0; i < animOnceNumber; i++) {
            ininviewAnim(i, resource);
        }
    }

    private synchronized void ininviewAnim(final int position, int resource) {
        int isoneGoTo = 0;
        final GameRoolBean bean = new GameRoolBean();
        View view = null;
        ImageView rl_head = null;
        try {
            view = LayoutInflater.from(mContext).inflate(resource, null);
            rl_head = (ImageView) view.findViewById(R.id.iv_head);
        } catch (Exception e) {
            new Exception("检查你的  resource  ");
        }
        view.setVisibility(GONE);
        final float ivWidht = GameUtil.getDimension(R.dimen.game_zww_bg_height);

        final ObjectAnimator animator = ofFloat(view, "translationX", -ivWidht, mWindowWidht);

        animList.add(animator);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(animDuration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        final int[] tag = {0};
        final View finalView1 = view;
        bean.animator = animator;
        bean.HEAD = rl_head;
        bean.VIEW = view;
        bean.boundWidth = GameUtil.dip2px(mContext, mRandom.nextInt(randomHeight));
//        Log.d(TAG, "ininviewAnim: itemViewStatePosition  bean.boundWidth  " + bean.boundWidth);
        animator.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationStart(Animator animation) {
                finalView1.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (mData == null) {
                    return;
                }

                if (itemViewStatePosition == animOnceNumber) {
                    itemViewStatePosition = 0;
                }
                if (mAninListener != null) {
                    mAninListener.onAnimationRepeat(bean.HEAD);
                }
                bean.boundWidth = GameUtil.dip2px(mContext, mRandom.nextInt(randomHeight));
                Integer game_id = mapIndex.get(mRandom.nextInt(mapIndex.size()));
                GameRoolBean gameRoolBean = mGameBaseMap.get(game_id);
                bean.HEAD.setImageResource(gameRoolBean.res);
                bean.id = game_id;

                // 显示条目
                bean.VIEW.setVisibility(VISIBLE);
                bean.HEAD.setVisibility(VISIBLE);

                bean.HEAD.setPadding((int) bean.boundWidth * 2, 0, 0, 0);
                invalidate();
                bean.position = itemViewStatePosition;
                itemViewStatePosition++;

                finalView1.setVisibility(VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                Log.d(TAG, "onAnimationEnd: ");

            }
        });

        final View finalView = view;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float view1Y;

            boolean tag = true;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (tag) {
                    view1Y += bean.boundWidth / 80F;
                } else {
                    view1Y -= bean.boundWidth / 80F;
                }
                if (view1Y > bean.boundWidth) {
                    tag = false;

                } else if (view1Y < 0) {
                    tag = true;
                }
                //设置高度
                finalView.setY(view1Y);
            }
        });

        rl_head.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAninListener != null) {
                    mAninListener.onClickItemView(bean.id, bean.HEAD);
                }
                final GameRoolBean gameRoolBean = mGameBaseMap.get(bean.id);

                startItemShake(bean.boundWidth, bean.VIEW, bean.HEAD);
                bean.HEAD.setImageResource(gameRoolBean.res_border);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bean.HEAD.setImageResource(gameRoolBean.res);
                    }
                }, 100);

            }
        });

//保存对象
        //添加对象
        rollAnimList.add(animator);

        beanList.add(bean);
        itemViewStatePosition++;
        this.addView(view/*, lp1*/);


    }

    //初始化动画
    private void initAnim(final View view, final int position, final float boundWidth) {
        final float ivWidht = GameUtil.getDimension(R.dimen.game_zww_bg_height);
        int toWindowWidgt = (int) ((mWindowWidht + ivWidht) * (position) / animOnceNumber);
        view.setVisibility(VISIBLE);
        int vTiem = animDuration / (animOnceNumber + 1);
        float AnimPositionTiem = animDuration - (toWindowWidgt / (mWindowWidht + ivWidht) * animDuration) - vTiem;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", toWindowWidgt, mWindowWidht);
        animList.add(animator);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float view1Y;
            boolean tag = true;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (tag) {
                    view1Y += boundWidth / 80F;
                } else {
                    view1Y -= boundWidth / 80F;
                }
                if (view1Y > boundWidth) {
                    tag = false;

                } else if (view1Y < 0) {
                    tag = true;
                }
                //设置高度
                view.setY(view1Y);
            }
        });

        animator.addListener(new AnimatorFactory() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rollAnimList.get(position).start();
            }
        });
        animator.setDuration((long) AnimPositionTiem).start();
    }

    //抖动item
    private void startItemShake(float boundHeight, View view, ImageView head) {
        int headWidthTo2 = head.getWidth() / 2;
        int viewHeightTo2 = head.getHeight() / 2;
        int paddingLeft = head.getPaddingLeft();
        int pxLeft = GameUtil.px2dip(head.getContext(), paddingLeft);
        head.setPivotX(pxLeft + headWidthTo2);
        head.setPivotY(viewHeightTo2);
        //360度旋转
        mItmeDouAnim = ofFloat(head, "rotation", 10, -10, 0);

        mItmeDouAnim.setDuration(150);
        mItmeDouAnim.setRepeatCount(2);
        mItmeDouAnim.start();

    }


    public int getWindowWidth() {

        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public void destroyView() {
        mHandler.removeCallbacks(animRun);
    }

    public GameDFJRollView setAninListener(AninListener l) {
        this.mAninListener = l;
        return this;
    }

    //不能连点操作  必须在初始化UI之后
    public void setmData(ArrayList<Integer> mapIndex, HashMap<Integer, GameRoolBean> mGameBaseMap) {
        if (mapIndex == null || mapIndex.size() == 0) {
            new Exception("不能连点操作  必须在初始化UI之后");
            return;
        }
        if (mapIndex == null || mapIndex.size() < beanList.size()) {
            Log.d(TAG, "setmData: 请检查你的数据集合 ==  beanList.size()  ??");
            return;
        }
        this.mGameBaseMap.putAll(mGameBaseMap);
        this.mapIndex.clear();
        this.mapIndex.addAll(mapIndex);
        notifyAllData();
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
        Integer game_id = mapIndex.get(mRandom.nextInt(mapIndex.size()));
        GameRoolBean gameRoolBean = mGameBaseMap.get(game_id);
        bean.HEAD.setImageResource(gameRoolBean.res);
        bean.id = game_id;

    }


    @Override
    protected void onDetachedFromWindow() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mItmeDouAnim!=null){
            mItmeDouAnim.removeAllListeners();
            mItmeDouAnim.removeAllUpdateListeners();
            mItmeDouAnim.cancel();
        }
        if (animList != null && animList.size() != 0) {
            for (ObjectAnimator anim : animList) {
                if (anim != null) {
                    anim.removeAllListeners();
                    anim.removeAllUpdateListeners();
                    anim.cancel();
                }
            }
        }
        removeAllViews();
        super.onDetachedFromWindow();
    }

    // 隐藏条目
    public void goneItmeView() {
        beanList.get(itemViewStatePosition).VIEW.setVisibility(GONE);
    }

    public GameDFJRollView setItmeBG(int itmeBG) {

        mItmeBG = itmeBG;
        return this;
    }


    public interface AninListener {
        void onClickItemView(int itemViewStatePosition, View view);

        void onAnimationRepeat(View view);

    }


    public GameDFJRollView setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
        return this;
    }

    public GameDFJRollView setAnimOnceNumber(int animOnceNumber) {
        this.animOnceNumber = animOnceNumber;
        return this;
    }


    public GameDFJRollView setBoundHeight(int boundHeight) {
        this.boundHeight = boundHeight;
        return this;
    }

    public ArrayList<GameRoolBean> getBeanList() {
        return beanList;
    }
}
