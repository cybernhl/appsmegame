package weking.lib.game.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import weking.lib.game.R;
import weking.lib.game.utils.GameAction;
import weking.lib.game.utils.GameUtil;

/**
 * 创建时间 2017/3/6.
 * 创建人 zhengb
 * 功能描述
 */

public class CowboyAnimFragment extends Fragment {

    // 时间
    private RelativeLayout mRl_com;
    // 背景
    private ImageView iv_com_bg;
    //
    private TextView tv_com_text;

    // 时间


    //结果
    private RelativeLayout mRl_result;
    // 赢钱的背景
    private ImageView im_result_win;

    // 庄家的钱
    private TextView tv_banker_money;
    // 自己的钱
    private TextView tv_this_money;
    //结果
    private boolean isShowAnim;
    private ScaleAnimation mHeidAnimation;
    private Handler mHandler;
    private ScaleAnimation mShowAnimation;

    public static CowboyAnimFragment newInstance() {
        CowboyAnimFragment fragemnt = new CowboyAnimFragment();
        return fragemnt;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_layout_cowboy_anim_fragment, null);
        initView(view);
        mHandler = new Handler();
        return view;
    }

    private void initView(View view) {
        mRl_com = (RelativeLayout) view.findViewById(R.id.rl_com);
        mRl_result = (RelativeLayout) view.findViewById(R.id.rl_result);
        tv_banker_money = (TextView) view.findViewById(R.id.tv_banker_money);
        tv_com_text = (TextView) view.findViewById(R.id.tv_com_text);
        tv_this_money = (TextView) view.findViewById(R.id.tv_this_money);
        im_result_win = (ImageView) view.findViewById(R.id.im_result_win);
        iv_com_bg = (ImageView) view.findViewById(R.id.iv_com_bg);
    }

    /**
     * 倒计时
     *
     * @param tiem
     */
    public void shouDownTiem(int tiem) {
        tv_com_text.setText(tiem + "");
    }


    /**
     * 倒计时 第一次
     *
     * @param tiem
     */
    public void shouOneDownTiem(int tiem) {
//        setConVisibility();
        iv_com_bg.setImageResource(R.drawable.game_cowcoy_countdown);
        tv_com_text.setText(tiem + "");
        showAmin(mRl_com);
    }

    /**
     * 显示下注的动画
     */
    public void shouBetArea() {

//        setConVisibility();
        showAmin(mRl_com);
        tv_com_text.setText("");
        iv_com_bg.setImageResource(R.drawable.game_cowboy_tip_bet_area);
    }

    /**
     * 下一局
     *
     * @param tiem
     */
    public void shouNextGame(int tiem) {
        tv_com_text.setText(getString(R.string.game_cowboy_next_game_tiem, tiem));

    }

    /**
     * 下一局 第一次
     *
     * @param tiem
     */
    public void shouOneNextGame(int tiem) {
//        setConVisibility();
//        showResultAmin(mRl_com);
        setViewVisibility(mRl_com);
        tv_com_text.setText(getString(R.string.game_cowboy_next_game_tiem, tiem));
        iv_com_bg.setImageResource(R.drawable.game_cowboy_tip_hundred_next);
    }

    /**
     * 等待下一局 第一次
     */
    public void shouOneWaitNextGame() {
//        setConVisibility();
        iv_com_bg.setImageResource(R.drawable.game_cowboy_tip_hundred_next);
        tv_com_text.setText(getString(R.string.game_cowboy_next_wait_game_tiem));
        showAmin(mRl_com);
    }

    /**
     * 设置结果
     *
     * @param isWinMoney
     * @param bankerMoney
     * @param thisMoney
     */
    public void shouNextGame(final boolean isWinMoney, int bankerMoney, int thisMoney, final GameAction.One<View> action) {
//        setResultVisibility();
        if (action != null) {
            action.invoke(tv_this_money);
        }
        if (isWinMoney) {
            im_result_win.setImageResource(R.drawable.game_cowboy_fight_win);
        } else {
            im_result_win.setImageResource(R.drawable.game_cowboy_fight_fail);
        }
        tv_banker_money.setText(getText(R.string.game_cowboy_banker) + "  " + GameUtil.intToString(bankerMoney));
        tv_this_money.setText(getText(R.string.game_cowboy_my_moeny) + "  " + GameUtil.intToString(thisMoney));
        showAmin(mRl_result);
    }


    /**
     * 设置时间的视图
     */
//    private void setConVisibility() {
//        if (mRl_com.getVisibility() == View.GONE) {
//            mRl_com.setVisibility(View.VISIBLE);
//        }
//        if (mRl_result.getVisibility() == View.VISIBLE) {
//            mRl_result.setVisibility(View.GONE);
//        }
//    }

//    /**
//     * 设置时间的视图
//     */
//    private void setResultVisibility() {
//        if (mRl_com.getVisibility() == View.VISIBLE) {
//            mRl_com.setVisibility(View.GONE);
//        }
//        if (mRl_result.getVisibility() == View.GONE) {
//            mRl_result.setVisibility(View.VISIBLE);
//        }
//    }
    private void setViewVisibility(View view) {
        if (view.equals(mRl_com)) {
            mRl_com.setVisibility(View.VISIBLE);
            mRl_result.setVisibility(View.INVISIBLE);
        } else {
            mRl_result.setVisibility(View.VISIBLE);
            mRl_com.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 隐藏倒计时
     */
    public void heidTime() {
        heidAmin(mRl_com);
    }

    /**
     * 隐藏结果
     */
    public void heidResult() {
        heidAmin(mRl_result);
    }

    /**
     * 显示动画
     *
     * @param view
     */
    private void showAmin(View view) {
        showAmin(view, null);
    }

    private void showAmin(final View view, final GameAction.Void action) {
        if (mShowAnimation == null) {
            mShowAnimation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mShowAnimation.setDuration(150);
        }
        mShowAnimation.setAnimationListener(

                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        setViewVisibility(view);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (action != null) {
                            action.invoke();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

        view.startAnimation(mShowAnimation);
    }

    /**
     * 显示动画
     *
     * @param view
     */
    private void heidAmin(View view) {
        heidAmin(view, null);
    }

    private void heidAmin(final View view, final GameAction.Void action) {
        if (mHeidAnimation == null) {
            mHeidAnimation = new ScaleAnimation(1f, 0.5f, 1f, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mHeidAnimation.setDuration(100);
        }
        mHeidAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (action != null) {
                    action.invoke();
                } else {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(mHeidAnimation);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
