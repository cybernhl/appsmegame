package weking.lib.game.dialog;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import app.io.weking.anim.bean.Gift;
import weking.lib.game.R;
import weking.lib.game.GameC;
import weking.lib.game.observer.GameObserver;


public class GameResultDialog extends BaseTipDialog implements View.OnClickListener {

    private RelativeLayout root_view;

    private ImageView targetView;

    private TextView tvWinMoney;

    private Context mContext;

    private View result_gift1;
    private View result_gift2;
    private View result_gift3;

    private ImageView gift_icon1;
    private ImageView gift_icon2;
    private ImageView gift_icon3;
    private TextView gift_charm1;
    private TextView gift_charm2;
    private TextView gift_charm3;


    private GamePlayActionListener playActionListener;
    private Gift selectGift;
    private List<Gift> gifts;

    private LinearLayout ll_gift;

    private boolean isPublish;

    public interface GamePlayActionListener {

        void sendGift(int giftId, int giftType);
    }

    public GameResultDialog(Context context, boolean isPublish) {
        super(context);
        mContext = context.getApplicationContext();
        this.isPublish = isPublish;
    }

    public void setPlayActionListener(GamePlayActionListener playActionListener) {
        this.playActionListener = playActionListener;
    }

    @Override
    protected void doBusiness() {
        tvWinMoney = (TextView) findViewById(R.id.num2);
        findViewById(R.id.dashang).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        root_view = (RelativeLayout) findViewById(R.id.root_view);
        ll_gift = (LinearLayout) findViewById(R.id.ll_gift);
        targetView = (ImageView) findViewById(R.id.targetView);
        result_gift1 = findViewById(R.id.result_gift1);
        result_gift2 = findViewById(R.id.result_gift2);
        result_gift3 = findViewById(R.id.result_gift3);
        gift_icon1 = (ImageView) result_gift1.findViewById(R.id.gift_icon);
        gift_icon2 = (ImageView) result_gift2.findViewById(R.id.gift_icon);
        gift_icon3 = (ImageView) result_gift3.findViewById(R.id.gift_icon);
        gift_charm1 = (TextView) result_gift1.findViewById(R.id.gift_charm);
        gift_charm2 = (TextView) result_gift2.findViewById(R.id.gift_charm);
        gift_charm3 = (TextView) result_gift3.findViewById(R.id.gift_charm);

        result_gift1.setOnClickListener(this);
        result_gift2.setOnClickListener(this);
        result_gift3.setOnClickListener(this);
        result_gift1.setBackgroundResource(R.drawable.game_win_selected);
        setPublish();
    }

    private void setPublish() {
        if (isPublish) {
            ll_gift.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.game_dialog_user_resut;
    }


    public void setGifView(List<Gift> gifts) {
        if (gifts == null || gifts.size() == 0) {
            ll_gift.setVisibility(View.GONE);
            return;
        } else {
            ll_gift.setVisibility(View.VISIBLE);
        }
        if (gifts == null && gifts.size() < 3) {
            gift_icon1.setVisibility(View.GONE);
            gift_icon2.setVisibility(View.GONE);
            gift_icon3.setVisibility(View.GONE);
            return;
        }
        this.gifts = gifts;
        result_gift1.setBackgroundResource(R.drawable.game_win_selected);
        result_gift2.setBackgroundResource(0);
        result_gift3.setBackgroundResource(0);
        selectGift = gifts.get(0);
        GameObserver.getAppObserver().loaderImage(gift_icon1, gifts.get(0).getPic_url());
        GameObserver.getAppObserver().loaderImage(gift_icon2, gifts.get(1).getPic_url());
        GameObserver.getAppObserver().loaderImage(gift_icon3, gifts.get(2).getPic_url());

        gift_charm1.setText(gifts.get(0).getPrice() + "");
        gift_charm2.setText(gifts.get(1).getPrice() + "");
        gift_charm3.setText(gifts.get(2).getPrice() + "");
    }

    public void setWinMoney(int money) {
        tvWinMoney.setText(" " + money + GameObserver.getAppObserver().getStringText(GameC.str.game_gold));
    }


    public void winDialogAnim() {
        final View mCopyView = new View(mContext);//新建副本View

        mCopyView.setBackgroundDrawable(targetView.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象

        int[] location = new int[2];
        int[] location_L = new int[2];
        int[] location_F = new int[2];
        targetView.getLocationOnScreen(location);  //获取目标View的相对屏幕位置
        root_view.getLocationOnScreen(location_L);     //获取父View相对屏幕位置
        tvWinMoney.getLocationOnScreen(location_F);     //获取父View相对屏幕位置
        params.leftMargin = location[0] - location_L[0];
        params.topMargin = location[1] - location_L[1];   //给副本View设置位置，与目标View重合
        params.width = targetView.getWidth();
        params.height = targetView.getHeight();
        root_view.addView(mCopyView, params);    //加入父View中
        int fromXDelta = location_F[0] - location[0];//337
        int fromYDelta = location_F[1] - location[1];//-1033
        TranslateAnimation fapai = new TranslateAnimation(fromXDelta, 0, fromYDelta, 0);
        fapai.setDuration(1000);
        mCopyView.bringToFront();
        mCopyView.setAnimation(fapai);
        fapai.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                root_view.removeView(mCopyView);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        fapai.startNow();
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager windowManager = window.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        lp.height = (int) (display.getHeight()); // 设置宽度
        getWindow().setAttributes(lp);

    }

    @Override
    public void onClick(View v) {
        if (gifts == null && gifts.size() < 3) {
            return;
        }
        if (v.getId() == R.id.result_gift1) {
            selectGift = gifts.get(0);
            result_gift1.setBackgroundResource(R.drawable.game_win_selected);
            result_gift2.setBackgroundResource(0);
            result_gift3.setBackgroundResource(0);
        } else if (v.getId() == R.id.result_gift2) {
            selectGift = gifts.get(1);
            result_gift1.setBackgroundResource(0);
            result_gift2.setBackgroundResource(R.drawable.game_win_selected);
            result_gift3.setBackgroundResource(0);
        } else if (v.getId() == R.id.result_gift3) {
            selectGift = gifts.get(2);
            result_gift1.setBackgroundResource(0);
            result_gift2.setBackgroundResource(0);
            result_gift3.setBackgroundResource(R.drawable.game_win_selected);
        } else if (v.getId() == R.id.dashang) {
            if (playActionListener != null) {
                playActionListener.sendGift(selectGift.getGift_id(), selectGift.getType());
            }
            cancel();
        }
    }
}
