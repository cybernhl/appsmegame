package weking.lib.game.utils;

import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.DimenRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.dialog.MyBaseTipDialog;
import weking.lib.game.event.GoPayListActivityEvent;
import weking.lib.game.observer.GameObserver;

/**
 * 创建时间 2017/8/9.
 * 创建人 frs
 * 功能描述
 */
public class GameUIUtils {

    /**
     * @param iv_zhou      // 轴
     * @param iv_line      //线
     * @param iv_zhua_righ //钩子
     * @param iv_zhua_left
     * @param iv_connect   /钩子与线的连接
     * @param view_toolbar 底部背景
     */
    public static void notityBetUI(int currentBet, ImageView iv_zhou,
                                   ImageView iv_line, ImageView iv_zhua_righ,
                                   ImageView iv_zhua_left, ImageView iv_connect, View view_toolbar,
                                   ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000
            , int my_money) {

//        if (currentBet > my_money) {
//            showCheckMoney();
//            return;
//        }
        initnotityZWWUI(my_money, currentBet, iv_bet10, iv_bet100, iv_bet1000);
        switch (currentBet) {
            case GameAnimUtil.BET_10:
                iv_zhou.setImageResource(R.drawable.game_zww_core_10);
                iv_zhua_left.setImageResource(R.drawable.game_zww_paw_l_10);
                iv_zhua_righ.setImageResource(R.drawable.game_zww_paw_r_10);
                iv_connect.setImageResource(R.drawable.game_zww_connect_10);
                iv_line.setBackgroundResource(R.drawable.game_zww_line_10);
                startScaleAnim(iv_bet10);
                break;
            case GameAnimUtil.BET_100:
                iv_zhou.setImageResource(R.drawable.game_zww_core_100);
                iv_zhua_left.setImageResource(R.drawable.game_zww_paw_l_100);
                iv_zhua_righ.setImageResource(R.drawable.game_zww_paw_r_100);
                iv_connect.setImageResource(R.drawable.game_zww_connect_100);
                iv_line.setBackgroundResource(R.drawable.game_zww_line_100);
                startScaleAnim(iv_bet100);
                break;

            case GameAnimUtil.BET_1000:
                startScaleAnim(iv_bet1000);
                iv_zhou.setImageResource(R.drawable.game_zww_core_1000);
                iv_zhua_left.setImageResource(R.drawable.game_zww_paw_l_1000);
                iv_zhua_righ.setImageResource(R.drawable.game_zww_paw_r_1000);
                iv_connect.setImageResource(R.drawable.game_zww_connect_1000);
                iv_line.setBackgroundResource(R.drawable.game_zww_line_1000);
                break;
        }

    }

    // 初始化打飞机的倍数按钮
    public static void initnotityZWWUI(int money, int currentBet, ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000) {
        if (money < GameAnimUtil.BET_10) {
            iv_bet10.setImageResource(R.drawable.game_zww_dount_10_no);
            iv_bet100.setImageResource(R.drawable.game_zww_dount_100_no);
            iv_bet1000.setImageResource(R.drawable.game_zww_dount_1000_no);

        } else if (money < GameAnimUtil.BET_100) {
            iv_bet10.setImageResource(R.drawable.game_zww_dount_10);
            iv_bet100.setImageResource(R.drawable.game_zww_dount_100_no);
            iv_bet1000.setImageResource(R.drawable.game_zww_dount_1000_no);

        } else if (money < GameAnimUtil.BET_1000) {
            iv_bet10.setImageResource(R.drawable.game_zww_dount_10_nor);
            iv_bet100.setImageResource(R.drawable.game_zww_dount_100_nor);
            iv_bet1000.setImageResource(R.drawable.game_zww_dount_1000_no);

            if (currentBet == GameAnimUtil.BET_100) {
                iv_bet100.setImageResource(R.drawable.game_zww_dount_100);
            } else {
                iv_bet10.setImageResource(R.drawable.game_zww_dount_10);
            }
        } else {
            iv_bet10.setImageResource(R.drawable.game_zww_dount_10_nor);
            iv_bet100.setImageResource(R.drawable.game_zww_dount_100_nor);
            iv_bet1000.setImageResource(R.drawable.game_zww_dount_1000_nor);

            if (currentBet == GameAnimUtil.BET_10) {
                iv_bet10.setImageResource(R.drawable.game_zww_dount_10);
            } else if (currentBet == GameAnimUtil.BET_100) {
                iv_bet100.setImageResource(R.drawable.game_zww_dount_100);
            } else if (currentBet == GameAnimUtil.BET_1000) {
                iv_bet1000.setImageResource(R.drawable.game_zww_dount_1000);
            }
        }
    }

    //抓娃娃不够是减倍数
    public static int zwwJianBetUi(int currentBet, int diamonds, ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000) {
        if (diamonds < currentBet) {
            if (currentBet == GameAnimUtil.BET_100) {
                iv_bet100.setImageResource(R.drawable.game_zww_dount_100_no);
                iv_bet10.setImageResource(R.drawable.game_zww_dount_10);
                return GameAnimUtil.BET_10;
            } else if (currentBet == GameAnimUtil.BET_1000) {
                iv_bet1000.setImageResource(R.drawable.game_zww_dount_1000_no);
                iv_bet100.setImageResource(R.drawable.game_zww_dount_100);
                return GameAnimUtil.BET_100;
            } else {
                return currentBet;
            }
        } else {
            return currentBet;
        }

    }

    //更新金币后的Ui
    public static void notityMoneyToBetUI(int diamonds, int currentBet, ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000) {
        if (diamonds > GameAnimUtil.BET_1000) {
            iv_bet10.setImageResource(R.drawable.game_zww_dount_10_nor);
            iv_bet100.setImageResource(R.drawable.game_zww_dount_100_nor);
            iv_bet1000.setImageResource(R.drawable.game_zww_dount_1000_nor);
        } else if (diamonds > GameAnimUtil.BET_100) {
            iv_bet10.setImageResource(R.drawable.game_zww_dount_10_nor);
            iv_bet100.setImageResource(R.drawable.game_zww_dount_100_nor);
        } else {
            iv_bet10.setImageResource(R.drawable.game_zww_dount_10_nor);
        }
        if (currentBet == GameAnimUtil.BET_10) {
            iv_bet10.setImageResource(R.drawable.game_zww_dount_10);
        } else if (currentBet == GameAnimUtil.BET_100) {
            iv_bet100.setImageResource(R.drawable.game_zww_dount_100);
        } else {
            iv_bet1000.setImageResource(R.drawable.game_zww_dount_1000);
        }
    }

    //缩放动画
    private static void startScaleAnim(ImageView iv_bet) {
        final ScaleAnimation animation = new ScaleAnimation(0.0f, 1.3f, 0.0f, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);
        iv_bet.startAnimation(animation);
    }

    // 初始化打飞机的倍数按钮
    public static void initnotityDFJUI(int money, int currentBet, ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000) {

        if (money < GameAnimUtil.BET_10) {
            iv_bet10.setImageResource(R.drawable.game_dfj_dount_10_no);
            iv_bet100.setImageResource(R.drawable.game_dfj_dount_100_no);
            iv_bet1000.setImageResource(R.drawable.game_dfj_dount_1000_no);
        } else if (money < GameAnimUtil.BET_100) {
            iv_bet10.setImageResource(R.drawable.game_dfj_dount_10);
            iv_bet100.setImageResource(R.drawable.game_dfj_dount_100_no);
            iv_bet1000.setImageResource(R.drawable.game_dfj_dount_1000_no);
        } else if (money < GameAnimUtil.BET_1000) {
            iv_bet10.setImageResource(R.drawable.game_dfj_dount_10_nor);
            iv_bet100.setImageResource(R.drawable.game_dfj_dount_100_nor);
            iv_bet1000.setImageResource(R.drawable.game_dfj_dount_1000_no);

            if (currentBet == GameAnimUtil.BET_100) {
                iv_bet100.setImageResource(R.drawable.game_dfj_dount_100);
            } else {
                iv_bet10.setImageResource(R.drawable.game_dfj_dount_10);
            }
        } else {
            iv_bet10.setImageResource(R.drawable.game_dfj_dount_10_nor);
            iv_bet100.setImageResource(R.drawable.game_dfj_dount_100_nor);
            iv_bet1000.setImageResource(R.drawable.game_dfj_dount_1000_nor);
            if (currentBet == GameAnimUtil.BET_10) {
                iv_bet10.setImageResource(R.drawable.game_dfj_dount_10);
            } else if (currentBet == GameAnimUtil.BET_100) {
                iv_bet100.setImageResource(R.drawable.game_dfj_dount_100);
            } else if (currentBet == GameAnimUtil.BET_1000) {
                iv_bet1000.setImageResource(R.drawable.game_dfj_dount_1000);
            }
        }
    }

    /**
     * 打飞机 加减倍数 更新UI
     */
    public static void notityDFJUI(View roomView, int money, int currentBet, View rl_game_bg, ImageView gun, ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000, ImageView view_fire) {

        initnotityDFJUI(money, currentBet, iv_bet10, iv_bet100, iv_bet1000);
        if (currentBet == GameAnimUtil.BET_5000) {
        } else if (currentBet == GameAnimUtil.BET_1000) {
            iv_bet1000.setImageResource(R.drawable.game_dfj_dount_1000);
            gun.setImageResource(R.drawable.game_dfj_cannon_1000);
            view_fire.setBackgroundResource(R.drawable.game_dfj_bg1000);
            startScaleAnim(iv_bet1000);
        } else if (currentBet == GameAnimUtil.BET_100) {
            iv_bet100.setImageResource(R.drawable.game_dfj_dount_100);
            gun.setImageResource(R.drawable.game_dfj_cannon_100);
            view_fire.setBackgroundResource(R.drawable.game_dfj_bg100);
            startScaleAnim(iv_bet100);
        } else {
            startScaleAnim(iv_bet10);
            iv_bet10.setImageResource(R.drawable.game_dfj_dount_10);
            gun.setImageResource(R.drawable.game_dfj_cannon_10);
            view_fire.setBackgroundResource(R.drawable.game_dfj_bg10);
        }
        roomView.invalidate();

    }

    /**
     * 棋牌 加减倍数 更新UI
     */

    public static void notityCradUI(int money, int currentBet, ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000, ImageView iv_bet5000) {
        initnotityCradUI(money, currentBet, iv_bet10, iv_bet100, iv_bet1000, iv_bet5000);
        if (currentBet == GameAnimUtil.BET_5000) {
            startScaleAnim(iv_bet5000);
            iv_bet5000.setImageResource(R.drawable.game_bet_5k);
        } else if (currentBet == GameAnimUtil.BET_1000) {
            iv_bet1000.setImageResource(R.drawable.game_bet_1k);
            startScaleAnim(iv_bet1000);
        } else if (currentBet == GameAnimUtil.BET_100) {
            iv_bet100.setImageResource(R.drawable.game_bet_100);
            startScaleAnim(iv_bet100);
        } else {
            iv_bet10.setImageResource(R.drawable.game_bet_10);
            startScaleAnim(iv_bet10);
        }
    }

    public static void initnotityCradUI(int money, int currentBet, ImageView iv_bet10, ImageView iv_bet100, ImageView iv_bet1000, ImageView iv_bet5000) {

        if (money < GameAnimUtil.BET_10) {
            iv_bet10.setImageResource(R.drawable.game_bet_base_no);
            iv_bet100.setImageResource(R.drawable.game_bet_base_no);
            iv_bet1000.setImageResource(R.drawable.game_bet_base_no);
            iv_bet5000.setImageResource(R.drawable.game_bet_base_no);
        } else if (money < GameAnimUtil.BET_100) {
            iv_bet10.setImageResource(R.drawable.game_bet_10);
            iv_bet100.setImageResource(R.drawable.game_bet_base_no);
            iv_bet1000.setImageResource(R.drawable.game_bet_base_no);
            iv_bet5000.setImageResource(R.drawable.game_bet_base_no);
        } else if (money < GameAnimUtil.BET_1000) {
            iv_bet10.setImageResource(R.drawable.game_bet_10);
            iv_bet100.setImageResource(R.drawable.game_bet_100_nor);
            iv_bet1000.setImageResource(R.drawable.game_bet_base_no);
            iv_bet5000.setImageResource(R.drawable.game_bet_base_no);
            if (currentBet == GameAnimUtil.BET_100) {
                iv_bet10.setImageResource(R.drawable.game_bet_10_nor);
                iv_bet100.setImageResource(R.drawable.game_bet_100);
            } else {
                iv_bet10.setImageResource(R.drawable.game_bet_10);
            }
        } else if (money < GameAnimUtil.BET_5000) {
            iv_bet10.setImageResource(R.drawable.game_bet_10_nor);
            iv_bet100.setImageResource(R.drawable.game_bet_100_nor);
            iv_bet1000.setImageResource(R.drawable.game_bet_1k_nor);
            iv_bet5000.setImageResource(R.drawable.game_bet_base_no);
            if (currentBet == GameAnimUtil.BET_1000) {
                iv_bet10.setImageResource(R.drawable.game_bet_10_nor);
                iv_bet100.setImageResource(R.drawable.game_bet_100_nor);
                iv_bet1000.setImageResource(R.drawable.game_bet_1k);
            } else if (currentBet == GameAnimUtil.BET_100) {
                iv_bet10.setImageResource(R.drawable.game_bet_10_nor);
                iv_bet100.setImageResource(R.drawable.game_bet_100);
            } else {
                iv_bet10.setImageResource(R.drawable.game_bet_10);
            }
        } else {
            iv_bet10.setImageResource(R.drawable.game_bet_10_nor);
            iv_bet100.setImageResource(R.drawable.game_bet_100_nor);
            iv_bet1000.setImageResource(R.drawable.game_bet_1k_nor);
            iv_bet5000.setImageResource(R.drawable.game_bet_5k_nor);
            if (currentBet == GameAnimUtil.BET_10) {
                iv_bet10.setImageResource(R.drawable.game_bet_10_nor);
            } else if (currentBet == GameAnimUtil.BET_100) {
                iv_bet100.setImageResource(R.drawable.game_bet_100_nor);
            } else if (currentBet == GameAnimUtil.BET_1000) {
                iv_bet1000.setImageResource(R.drawable.game_bet_1k_nor);
            } else if (currentBet == GameAnimUtil.BET_1000) {
                iv_bet1000.setImageResource(R.drawable.game_bet_5k_nor);
            } else {
                iv_bet10.setImageResource(R.drawable.game_bet_10_nor);
            }
        }
    }

    /**
     * 设置view 的宽度
     *
     * @param view
     * @param width
     */
    public static void setViewLayoutParamsWidth(View view, float width) {
        ViewGroup.LayoutParams layoutParams_left = view.getLayoutParams();
        layoutParams_left.width = (int) (width);
        view.setLayoutParams(layoutParams_left);
    }

    /**
     * 设置view 的margin
     *
     * @param view
     * @param width
     */
    public static void setViewLayoutParamsMargin(View view, float width) {
        setViewLayoutParamsMargin(view, width, R.dimen.game_pai_item_width, R.dimen.game_pai_item_height);
    }

    /**
     * 设置view 的margin
     *
     * @param view
     * @param width
     */
    public static void setViewLayoutParamsMargin(View view, float width, @DimenRes int dimenResidWidth, @DimenRes int dimenResidHeight) {
        RelativeLayout.LayoutParams lp_4 = new RelativeLayout.LayoutParams((int) GameUtil.getDimension(dimenResidWidth), (int) GameUtil.getDimension(dimenResidHeight));
//        int dpTopx = DisplayUtil.dip2px(view.getContext(), width);
//        int pxTodp = DisplayUtil.px2dip(view.getContext(), width);
        lp_4.leftMargin = (int) width;
        view.setLayoutParams(lp_4);
    }

    public static ImageView showFireWinAnim(ViewGroup rootView, View rollItemView) {
        final ImageView mCopyView = new ImageView(rollItemView.getContext());//新建副本View
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象
        int[] location = new int[2];
        rollItemView.getLocationOnScreen(location);  //获取目标View的相对屏幕位置
        params.width = location[0];
        params.height = location[1];
        final AnimationDrawable animationDrawable = (AnimationDrawable) mCopyView.getDrawable();
        animationDrawable.start();
        rootView.addView(mCopyView);
        return mCopyView;
    }

    /**
     * 获取牌的图片
     *
     * @param gameType
     * @return
     */
    public static int getPokerRes(int gameType) {
        int pokerBgRes = 0;
        switch (gameType) {
            case GameC.Game.GAME_TYPE_NIUNIU:
                pokerBgRes = R.drawable.game_card_poker_niuniu;
                break;
            case GameC.Game.GAME_TYPE_TTDZ:
                pokerBgRes = R.drawable.game_card_poker_dezhou;
                break;
            case GameC.Game.GAME_TYPE_ZJH:
                pokerBgRes = R.drawable.game_card_poker_zjh;
                break;
            case GameC.Game.GAME_TYPE_HLNZ:
                pokerBgRes = R.drawable.game_card_poker_cowboy;
                break;
        }
        return pokerBgRes;
    }

    public static void notityItemBgBetUI(int bet, ImageView gift) {
        switch (bet) {
            case GameAnimUtil.BET_10:
                gift.setBackgroundResource(R.drawable.game_zww_board_10);
                break;
            case GameAnimUtil.BET_100:
                gift.setBackgroundResource(R.drawable.game_zww_board_100);
                break;
            case GameAnimUtil.BET_1000:
                gift.setBackgroundResource(R.drawable.game_zww_board_1000);
                break;

            default:
                gift.setBackgroundResource(R.drawable.game_zww_board_10);
                break;
        }

    }

    public static int getBetRes(int bet) {
        int res;
        switch (bet) {
            case GameAnimUtil.BET_10:
                res = R.drawable.icon_exchange_sca;
                break;
            case GameAnimUtil.BET_100:
                res = R.drawable.icon_exchange_sca;
                break;
            case GameAnimUtil.BET_1000:
                res = R.drawable.icon_exchange_sca;
                break;
            case GameAnimUtil.BET_5000:
                res = R.drawable.icon_exchange_sca;
                break;
            default:
                res = R.drawable.icon_exchange_sca;
                break;
        }
        return res;
    }

    public static boolean checkMoney(int money) {
        if (money < GameAnimUtil.BET_10) {
            // 余额不足，提示充值
            final MyBaseTipDialog dialog = new MyBaseTipDialog(GameObserver.getAppObserver().getCurrentActivity());
            dialog.show();
            dialog.setMessage(R.string.gift_recharge_tip2);
            dialog.setOkButtonText(R.string.confirm);
            dialog.setOnOkButtonListener(new MyBaseTipDialog.OnOkButtonListener() {
                @Override
                public void onClick() {
                    goPayListActivity();
                    dialog.dismiss();
                }
            });
            return false;
        } else {
            return true;
        }
    }


    public static void showCheckMoney() {
        final MyBaseTipDialog dialog = new MyBaseTipDialog(GameObserver.getAppObserver().getCurrentActivity());
        dialog.show();
        dialog.setMessage(R.string.gift_recharge_tip2);
        dialog.setOkButtonText(R.string.confirm);
        dialog.setOnOkButtonListener(new MyBaseTipDialog.OnOkButtonListener() {
            @Override
            public void onClick() {
                goPayListActivity();
                dialog.dismiss();
            }
        });
    }


    private static void goPayListActivity() {
        EventBus.getDefault().post(new GoPayListActivityEvent());
    }


    public static int getTotalDuration(AnimationDrawable animationDrawable) {
        int iDuration = 0;

        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            iDuration += animationDrawable.getDuration(i);
        }
        return iDuration;
    }

}
