package weking.lib.game.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.bean.Poker;
import weking.lib.game.observer.GameObserver;
import weking.lib.utils.LogUtils;

public class GameUtil {
    private static int mWindowWidth;
    private static int mWindowHeight;

    public static float getDimension(@DimenRes int dimenResid) {
        if (GameObserver.getAppObserver().getApp() == null) {
            LogUtils.e("no  init  GameObserver  >>");
            return 0.0F;
        }
        return GameObserver.getAppObserver().getApp().getResources().getDimension(dimenResid);
    }

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        if (mWindowWidth == 0) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            return width;
        } else {
            return mWindowWidth;
        }
    }

    /**
     *  设置dialog 的宽度
     * @param dialog
     */
    public static void setDialogWidth(Dialog dialog) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (getWindowWidth(GameObserver.getAppObserver().getApp()) * 0.8f); // 设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    public static int getWindowHeight(Context context) {
        if (mWindowHeight == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();

            return height;
        } else {
            return mWindowHeight;
        }
    }

    // 获取屏幕位置
    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        return location;
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F);
    }

    public static String getAccessToken() {
        String key = GameObserver.getAppObserver().getStringText(GameC.str.ACCESS_TOKEN);
        return (String) GameObserver.getAppObserver().getObject(key, "");
    }

    // 获取房间id
    public static int getRoomLiveId() {
        return GameObserver.getAppObserver().getInteger(GameC.str.IN_ROOM_LIVE_ID);
    }

    public static RxAppCompatActivity getAppCompatActivity() {
        return (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity();
    }

    public static void setViewLayoutParamsWidth(View view, float width) {
        ViewGroup.LayoutParams layoutParams_left = view.getLayoutParams();
        layoutParams_left.width = ((int) width);
        view.setLayoutParams(layoutParams_left);
    }

    public static int getMoney() {
        String key = GameObserver.getAppObserver().getStringText(GameC.str.MY_DIAMONDS);
        return ((Integer) GameObserver.getAppObserver().getObject(key, Integer.valueOf(0))).intValue();
    }

    public static void closeAnimationDrawable(AnimationDrawable animationDrawable) {
        if (animationDrawable != null) {
            animationDrawable.stop();
            for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
                Drawable frame = animationDrawable.getFrame(i);
                if (frame instanceof BitmapDrawable) {
                    ((BitmapDrawable) frame).getBitmap().recycle();
                }
                frame.setCallback(null);
            }
            animationDrawable.setCallback(null);
        }
    }

    /**
     * 获取总下注的数字
     *
     * @param num
     * @return
     */
    public static String formatBetText(int num) {
        int absNum;
        String srtNum = null;
        if (num < 0) {
            absNum = Math.abs(num);
        } else {
            absNum = num;
        }
        if (absNum < 1000) {
            return String.valueOf(absNum);
        } else if (absNum < 100000) {
            double d = absNum;
            srtNum = String.format("%.2f", d / 1000) + "K";
        } else if (absNum < 1000000) {
            double d = absNum;
            srtNum = String.format("%.1f", d / 1000) + "K";
        } else {
            double d = absNum;
            srtNum = String.format("%.0f", d / 1000) + "K";
        }
        if (num < 0) {
            return "-" + srtNum;
        } else {
            return srtNum;
        }
    }

    /**
     * int装string
     *
     * @param integer
     * @return
     */
    public static String intToString(Integer integer) {
        if (integer > 0) {
            return "+" + integer;
        } else if (integer < 0) {
            return "" + integer;
        }
        return "0";
    }

    /**
     * 获取牌类型
     *
     * @param card_type
     * @return
     */
    public static String getCowboyCradType(int card_type) {
        int strRes = 0;
        switch (card_type) {
            case Poker.NN_NIU0:
                strRes = R.string.game_cowboy_no_niu;
                break;
            case Poker.NN_NIU1:
                strRes = R.string.game_cowboy_niu1;
                break;
            case Poker.NN_NIU2:
                strRes = R.string.game_cowboy_niu2;
                break;
            case Poker.NN_NIU3:
                strRes = R.string.game_cowboy_niu3;
                break;
            case Poker.NN_NIU4:
                strRes = R.string.game_cowboy_niu4;
                break;
            case Poker.NN_NIU5:
                strRes = R.string.game_cowboy_niu5;
                break;
            case Poker.NN_NIU6:
                strRes = R.string.game_cowboy_niu6;
                break;
            case Poker.NN_NIU7:
                strRes = R.string.game_cowboy_niu7;
                break;
            case Poker.NN_NIU8:
                strRes = R.string.game_cowboy_niu8;
                break;
            case Poker.NN_NIU9:
                strRes = R.string.game_cowboy_niu9;
                break;
            case Poker.NN_NIU10:
                strRes = R.string.game_cowboy_niuniu;
                break;
            default:
                strRes = R.string.game_cowboy_no_niu;
                break;
        }
        return GameObserver.getAppObserver().getCurrentActivity().getString(strRes);
    }
}

