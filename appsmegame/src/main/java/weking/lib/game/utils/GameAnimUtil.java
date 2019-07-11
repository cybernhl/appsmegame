
package weking.lib.game.utils;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import weking.lib.game.R;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.presenter.AnimatorFactory;
import weking.lib.game.view.game_2.DFJLayout;
import weking.lib.game.view.goodview.GoodView;

import static android.view.View.VISIBLE;


public class GameAnimUtil {
    public static final int BET_10 = 10;
    public static final int BET_50 = 50;
    public static final int BET_100 = 100;
    public static final int BET_500 = 500;
    public static final int BET_1000 = 1000;
    public static final int BET_5000 = 5000;


    /**
     * 发一张牌的动画
     *
     * @param startView
     * @param targetView
     * @param root_container
     * @param leftMargin
     * @param bet
     * @param l
     */
    public static void myAddBet(View startView, final View targetView, final ViewGroup root_container, int bet, int leftMargin, Integer topMargin, final GameLiveUtilListener l) {
        final ImageView mCopyView = new ImageView(GameObserver.getAppObserver().getApp());//新建副本View

        int pokerRes = GameUIUtils.getBetRes(bet);
        if (pokerRes != 0) {
            mCopyView.setImageResource(pokerRes);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象

        int[] location = new int[2];
        int[] location_L = new int[2];
        int[] location_F = new int[2];
        targetView.getLocationOnScreen(location);  //获取目标View的相对屏幕位置
        root_container.getLocationOnScreen(location_L);     //获取父View相对屏幕位置
        startView.getLocationOnScreen(location_F);     //获取父View相对屏幕位置
        params.leftMargin = location[0] - location_L[0] + leftMargin;
        params.topMargin = location[1] - location_L[1] + topMargin;   //给副本View设置位置，与目标View重合
        params.width = startView.getWidth();
        params.height = startView.getHeight();
        root_container.addView(mCopyView, params);    //加入父View中

        TranslateAnimation faPai = new TranslateAnimation(location_F[0] - location[0] - leftMargin, 0, location_F[1] - location[1] - topMargin, 0);
        faPai.setDuration(300);
        mCopyView.bringToFront();
        mCopyView.setAnimation(faPai);
        faPai.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                root_container.removeView(mCopyView);
                if (l != null) {
                    l.animEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        faPai.startNow();
    }


    /**
     * 发一张牌的动画
     *
     * @param startView
     * @param targetView
     * @param root_container
     * @param gameType
     * @param l
     */
    public static void faPaiAnimWithPoker(View startView, final View targetView, final ViewGroup root_container, int gameType, final GameLiveUtilListener l) {
        final View mCopyView = new View(GameObserver.getAppObserver().getApp());//新建副本View

        int pokerRes = GameUIUtils.getPokerRes(gameType);
        if (pokerRes != 0) {
            mCopyView.setBackgroundResource(pokerRes);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象

        int[] location = new int[2];
        int[] location_L = new int[2];
        int[] location_F = new int[2];
        targetView.getLocationOnScreen(location);  //获取目标View的相对屏幕位置
        root_container.getLocationOnScreen(location_L);     //获取父View相对屏幕位置
        startView.getLocationOnScreen(location_F);     //获取父View相对屏幕位置
        params.leftMargin = location[0] - location_L[0];
        params.topMargin = location[1] - location_L[1];   //给副本View设置位置，与目标View重合
        params.width = targetView.getWidth();
        params.height = targetView.getHeight();
        root_container.addView(mCopyView, params);    //加入父View中

        TranslateAnimation faPai = new TranslateAnimation(location_F[0] - location[0], 0, location_F[1] - location[1], 0);
        faPai.setDuration(300);
        mCopyView.bringToFront();
        mCopyView.setAnimation(faPai);
        faPai.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                root_container.removeView(mCopyView);
                if (l != null) {
                    l.animEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        faPai.startNow();
    }

    /**
     * 发牌到中间的动画
     *
     * @param targetView
     * @param root_container
     * @param gameType
     * @param action
     */
//  开始旋转
    private static int fireAngleNew = 10;
    private static int faPaiIndex = 0;
    private static int startRotation = 5;
    private static final ArrayList<View> arrayList = new ArrayList<>();

    public static void faPaiAnimWithPokerPing(final View targetView, final ViewGroup root_container, int gameType, final GameAction.OnResult<ArrayList<View>> action, Handler handler) {
        faPaiIndex++;
        // 发牌的张数
        int windowWidth = GameUtil.getWindowWidth(GameObserver.getAppObserver().getApp());
        int windowHeight = GameUtil.getWindowHeight(GameObserver.getAppObserver().getApp());

        final View mCopyView = new View(GameObserver.getAppObserver().getApp());//新建副本View

        final int pokerRes = GameUIUtils.getPokerRes(gameType);
        if (pokerRes != 0) {
            mCopyView.setBackgroundResource(pokerRes);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象

        int[] location_targe = new int[2];
        int[] location_root = new int[2];
        int[] location_start = {0, 150};
        targetView.getLocationOnScreen(location_targe);  //获取目标View的相对屏幕位置
        root_container.getLocationOnScreen(location_root);     //获取父View相对屏幕位置
//            startView.getLocationOnScreen(location_F);     //获取父View相对屏幕位置
        params.leftMargin = location_targe[0] - location_root[0];
        params.topMargin = location_targe[1] - location_root[1];   //给副本View设置位置，与目标View重合

        params.width = (int) (targetView.getWidth() * 1.5);
        params.height = (int) (targetView.getHeight() * 1.5);
        params.setMargins(faPaiIndex * 3 + location_targe[0] - params.width / 2 - faPaiIndex * 3 / 2, faPaiIndex * 3 + location_targe[1] - params.height / 2 - faPaiIndex * 3 / 2, 0, 0);
        root_container.addView(mCopyView, params);    //加入父View中
        arrayList.add(mCopyView);
        TranslateAnimation faPai = new TranslateAnimation(location_start[0] - location_targe[0], 0, location_start[1] - location_targe[1], 0);
        faPai.setDuration(300);
        mCopyView.bringToFront();
        mCopyView.setAnimation(faPai);

        if (action != null) {

            faPai.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCopyView.setPivotX(0);
                    int i = targetView.getHeight() * 2 - 50;
                    mCopyView.setPivotY(i);
                    // 设置角度
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
                    ObjectAnimator pokerRotation = ObjectAnimator.ofFloat(mCopyView, "rotation", 0, fireAngleNew);

                    pokerRotation.setDuration(300).start();
                    pokerRotation.addListener(new AnimatorFactory() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            faPaiIndex = 0;
                            if (action != null) {
                                action.invoke(arrayList);
                            }
                            super.onAnimationEnd(animation);
                        }
                    });
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        faPai.startNow();
    }


    /**
     * 收一张牌动画
     *
     * @param startView      开始
     * @param targetView
     * @param root_container 视图顶级的view
     */

    public static void shouPaiAnimWithPoker(View startView, final View targetView, final ViewGroup root_container, int gameType) {
        final View mCopyView = new View(GameObserver.getAppObserver().getApp());//新建副本View
        int pokerRes = GameUIUtils.getPokerRes(gameType);
        if (pokerRes != 0) {
            mCopyView.setBackgroundResource(pokerRes);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象

        int[] location = new int[2];
        int[] location_L = new int[2];
        int[] location_F = new int[2];
        targetView.getLocationOnScreen(location);  //获取目标View的相对屏幕位置
        root_container.getLocationOnScreen(location_L);     //获取父View相对屏幕位置
        startView.getLocationOnScreen(location_F);     //获取父View相对屏幕位置
        params.leftMargin = location[0] - location_L[0];
        params.topMargin = location[1] - location_L[1];   //给副本View设置位置，与目标View重合
        params.width = startView.getWidth();
        params.height = startView.getHeight();
        root_container.addView(mCopyView, params);    //加入父View中

        TranslateAnimation faPai = new TranslateAnimation(location_F[0] - location[0], 0, location_F[1] - location[1], 0);
        faPai.setDuration(500);
        mCopyView.bringToFront();
        mCopyView.setAnimation(faPai);
        faPai.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                root_container.removeView(mCopyView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        faPai.startNow();
    }



    public static void shouxiaZhuAnimWithPoker(ImageView fromView, final View targetView, final ViewGroup root_container, final GameLiveUtilListener l) {

        final ImageView mCopyView = new ImageView(GameObserver.getAppObserver().getApp());//新建副本View
        mCopyView.setImageDrawable(fromView.getDrawable());
//        mCopyView.setBackgroundDrawable(fromView.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象

        int[] location = new int[2];
        int[] location_L = new int[2];
        int[] location_F = new int[2];
        targetView.getLocationOnScreen(location);  //获取目标View的相对屏幕位置
        root_container.getLocationOnScreen(location_L);     //获取父View相对屏幕位置
        fromView.getLocationOnScreen(location_F);     //获取父View相对屏幕位置
        params.leftMargin = location[0] - location_L[0];
        params.topMargin = location[1] - location_L[1];   //给副本View设置位置，与目标View重合
        params.width = (fromView.getWidth());
        params.height = (fromView.getHeight());
        root_container.addView(mCopyView, params);    //加入父View中

        TranslateAnimation xiazhu = new TranslateAnimation(location_F[0] - location[0] + 10,
                targetView.getWidth() / 2 - fromView.getWidth() / 2, location_F[1] - location[1],
                targetView.getHeight() / 2 - fromView.getHeight() / 2);
        xiazhu.setDuration(500);
        mCopyView.bringToFront();
        mCopyView.setAnimation(xiazhu);
        xiazhu.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                root_container.removeView(mCopyView);
                targetView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        xiazhu.startNow();
    }


    /**
     * 放大动画
     *
     * @param view
     * @return
     */
    public static void toBigAnim(View view) {
        ScaleAnimation showAnimation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        showAnimation.setDuration(300);
        view.startAnimation(showAnimation);
    }


    /**
     * 向上浮动动画
     *
     * @param rollItemView
     * @param text
     * @param typeFace
     * @param textColor
     */
    public static void showToTopAnim(View rollItemView, String text, Typeface typeFace, int textColor) {
        GoodView goodView = new GoodView(rollItemView.getContext(), textColor);
        goodView.setText(text, typeFace);
        goodView.setTextSize(11);
        goodView.showTop(rollItemView);
    }



    public interface GameLiveUtilListener {
        void animEnd();
    }

    public static void startWinTextAnim(final DFJLayout rootView, View rollItemView, int money, boolean isStopView, Typeface mTypeFace) {
        if (isStopView) {
            return;
        }
        if (rootView == null) {
            return;
        }
        GoodView goodView = new GoodView(rootView.getContext(), R.color.game_app_theme_color);
        goodView.setText("+" + money + " ", mTypeFace);

        goodView.show(rollItemView);

    }

    /**
     * 赢钱的的动画
     *
     * @param rootView 最大的视图
     * @param toView   到哪里
     * @param fromView 从哪里
     */
    public static void startWinAnim(final ViewGroup rootView, View fromView, View toView) {
        startWinAnim(rootView, fromView, toView, false);
    }

    public static void startWinAnim(final ViewGroup rootView, View fromView, View toView, boolean isStopView) {
        final ImageView mCopyView = new ImageView(rootView.getContext());//新建副本View
        mCopyView.setImageResource(R.drawable.icon_exchange_sca);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象

        int[] location = new int[2];
        int[] location_L = new int[2];
        int[] location_F = new int[2];
        fromView.getLocationOnScreen(location);  //获取目标View的相对屏幕位置
        rootView.getLocationOnScreen(location_L);     //获取父View相对屏幕位置
        toView.getLocationOnScreen(location_F);     //获取父View相对屏幕位置
        int fromXDelta = location_F[0] - location[0];//337
        int fromYDelta = location_F[1] - location[1];//-1033
        if (isStopView) {
            params.leftMargin = GameUtil.getWindowWidth(rootView.getContext()) - location_L[0] + fromView.getWidth() / 2;
            params.topMargin = location[1] - location_L[1] + fromView.getHeight() / 2;
//            params.leftMargin = 100;
//            params.topMargin = 100;
            fromXDelta = location_F[0] - params.leftMargin;//337
            fromYDelta = location_F[1] - params.topMargin;//-1033
        } else {
            params.leftMargin = location[0] - location_L[0];
            params.topMargin = location[1] - location_L[1];   //给副本View设置位置，与目标View重合
            fromXDelta = location_F[0] - location[0];//337
            fromYDelta = location_F[1] - location[1];//-1033
        }
        params.width = toView.getWidth();
        params.height = toView.getHeight();
        rootView.addView(mCopyView, params);    //加入父View中

        TranslateAnimation fapai = new TranslateAnimation(0 + fromView.getWidth() / 2, fromXDelta/* + mWindowHeight / 6*/, 0 + fromView.getHeight() / 2, fromYDelta /*+ mWindowWidth / 6*/);
        fapai.setDuration(500);
        mCopyView.bringToFront();
        mCopyView.setAnimation(fapai);
        fapai.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rootView.removeView(mCopyView);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        });
        fapai.startNow();

    }


    /**
     * 获取文字宽度
     */
    public static int getTextWidth(TextView textView) {
        Paint paint = textView.getPaint();
        String str = textView.getText().toString();
        return (int) paint.measureText(str);
    }
}
