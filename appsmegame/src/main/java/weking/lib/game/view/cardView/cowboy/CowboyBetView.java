package weking.lib.game.view.cardView.cowboy;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

import weking.lib.game.R;
import weking.lib.game.utils.GameAction;
import weking.lib.game.utils.GameUIUtils;
import weking.lib.game.utils.GameUtil;

/**
 * 欢乐牛仔
 * 下注区域
 */

public class CowboyBetView extends RelativeLayout {

    private Context mContext;
    private Random mRandom;
    private int mRoomWidth;
    private int mRoomHeight;
    // 图片宽度
    private int mIcomWidht = 25;
    // 集合大小
    private int imageListSize = 45;
    private ArrayList<ImageView> mImageViews;
    //下注的img大小
    private int mBetImgWidth;

    public CowboyBetView(Context context) {
        this(context, null);
    }

    public CowboyBetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
//        LayoutInflater.from(context).inflate(R.layout.game_layout_cowboy_bet_view, null);
        mRandom = new Random();
        mImageViews = new ArrayList<>();
        int icomWidht = GameUtil.dip2px(context, mIcomWidht);
        mRoomWidth = (int) GameUtil.getDimension(R.dimen.game_poker_info_width) - icomWidht;
        mRoomHeight = (int) GameUtil.getDimension(R.dimen.game_cowboy_poker_info_bet_view_height) - icomWidht;
        mBetImgWidth = GameUtil.dip2px(mContext, mIcomWidht);


    }

    /**
     * 添加下注
     *
     * @param bet
     */
    public void addBet(int bet) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象
        int imageWigth = mRandom.nextInt(mRoomWidth);
        int imageHeight = mRandom.nextInt(mRoomHeight);
        if (mImageViews.size() != imageListSize) {
            ImageView imageView = new ImageView(mContext);
            mImageViews.add(imageView);
            setImageRes(imageView, bet);
            params.leftMargin = imageWigth;
            params.topMargin = imageHeight;
            params.width = mBetImgWidth;
            params.height = mBetImgWidth;
            this.addView(imageView, params);
        } else {
            ImageView imageView = mImageViews.get(mRandom.nextInt(imageListSize - 1));
            imageView.bringToFront();
            setImageRes(imageView, bet);
        }
    }

    /**
     * 显示自己下注
     *
     * @param imageView
     */
    public void showMyBetView(ImageView imageView) {
        imageView.setVisibility(VISIBLE);
    }

    /**
     * 添加自己下注
     *
     * @param bet
     * @param action
     */
    public void addMyBet(int bet, GameAction.Three<ImageView, Integer, Integer> action) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  //建立layout属性对象
        int imageWigth = mRandom.nextInt(mRoomWidth);
        int imageHeight = mRandom.nextInt(mRoomHeight);
        ImageView imageView = null;
        if (mImageViews.size() != imageListSize) {
            imageView = new ImageView(mContext);
            mImageViews.add(imageView);
            setImageRes(imageView, bet);
            params.leftMargin = imageWigth;
            params.topMargin = imageHeight;
            params.width = mBetImgWidth;
            params.height = mBetImgWidth;
            addView(imageView, params);

            if (action != null) {
                action.invoke(imageView, imageWigth, imageHeight);
            }
        } else {
            imageView = mImageViews.get(mRandom.nextInt(imageListSize - 1));
            imageView.bringToFront();
            setImageRes(imageView, bet);
            if (action != null) {
                action.invoke(imageView, 0, 0);
            }
        }
        imageView.setVisibility(INVISIBLE);

    }

    /**
     * 清除bet对象
     */
    public void colseImageList() {
        for (ImageView view : mImageViews) {
            if (view != null) {
                removeView(view);
            }
        }
        mImageViews.clear();
    }

    /**
     * 设置图片
     *
     * @param image
     * @param bet
     */
    private void setImageRes(ImageView image, int bet) {
        int betRes = GameUIUtils.getBetRes(bet);
        image.setImageResource(betRes);
    }

}
