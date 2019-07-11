package weking.lib.game.observer;

import android.content.Context;
import android.widget.ImageView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.HashMap;

import weking.lib.game.bean.GiftListBean;
import weking.lib.game.bean.SendGiftResult;
import weking.lib.game.utils.GameAction;

public abstract interface GameObservable {
    public abstract Context getApp();

    public abstract String getStringText(String paramString);

    public abstract Object getObjectText(String paramString, Object paramObject);


    public abstract int getInteger(String paramString);

    public abstract void loaderImage(ImageView paramImageView, String paramString);

    public abstract void loaderShortImage(ImageView paramImageView, String paramString);

    public abstract void setObject(String paramString, Object paramObject);

    public abstract Object getObject(String paramString, Object paramObject);

    public abstract void showLog(String paramString1, String paramString2);

    public abstract void loaderImage(ImageView paramImageView, String paramString, int paramInt);

    public abstract void sendGift(int paramInt1, String paramString, int paramInt2, GameAction.OnResult<SendGiftResult> paramOnResult);

    public abstract RxAppCompatActivity getCurrentActivity();

    public abstract GiftListBean getGiftBean(int paramInt);

    public abstract HashMap<Integer, GiftListBean> getGiftMap();

    /**
     *  弹出其他人的信息
     * @param account
     */
    void showOtherInfoDialog(String account );

}

