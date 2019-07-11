package weking.lib.game.view.cardView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import app.io.weking.anim.utils.AnimDisplayUtil;


/**
 * Created by jameson on 8/30/16.
 */
public class CardScaleHelper {
    private RecyclerView mRecyclerView;
    private Context mContext;

    private float mScale = 1f; // 两边视图scale
    private float mAlpha = 0.4f; // 两边视图Alpha
//    private int mPagePadding = 50; // 卡片的padding, 卡片间的距离等于2倍的mPagePadding
//    private int mShowLeftCardWidth = 15;   // 左边卡片显示大小


    private int mPagePadding = 25; // 卡片的padding, 卡片间的距离等于2倍的mPagePadding  ok  20
    private int mShowLeftCardWidth = 50;   // 左边卡片显示大小

//    private int mPagePadding = 70; // 卡片的padding, 卡片间的距离等于2倍的mPagePadding
//    private int mShowLeftCardWidth = 35;   // 左边卡片显示大小

    private int mCardWidth; // 卡片宽度
    private int mOnePageWidth; // 滑动一页的距离
    private int mCardGalleryWidth;

    private int mCurrentItemPos;
    private int mCurrentItemOffset;

    private CardLinearSnapHelper mLinearSnapHelper = new CardLinearSnapHelper();
    private CardScrolledLister mCardScrolledLister;

    public CardScaleHelper(int mPagePadding, int mShowLeftCardWidth) {
        this.mPagePadding = mPagePadding;
        this.mShowLeftCardWidth = mShowLeftCardWidth;
    }

    public void attachToRecyclerView(final RecyclerView mRecyclerView) {
        // 开启log会影响滑动体验, 调试时才开启
//        LogUtils.mLogEnable = false;
        this.mRecyclerView = mRecyclerView;
        mContext = mRecyclerView.getContext();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mCardScrolledLister != null) {
                        mCardScrolledLister.ScrolledToPos(mCurrentItemPos);
                    }
                    mLinearSnapHelper.mNoNeedToScroll = mCurrentItemOffset == 0 || mCurrentItemOffset == getDestItemOffset(mRecyclerView.getAdapter().getItemCount() - 1);
                } else {
                    mLinearSnapHelper.mNoNeedToScroll = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // dx>0则表示右滑, dx<0表示左滑, dy<0表示上滑, dy>0表示下滑
                mCurrentItemOffset += dx;
                computeCurrentItemPos();
//                LogUtils.v(String.format("dx=%s, dy=%s, mScrolledX=%s", dx, dy, mCurrentItemOffset));
                onScrolledChangedCallback();
            }
        });

        initWidth();
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 初始化卡片宽度
     */
    private void initWidth() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mCardGalleryWidth = mRecyclerView.getWidth();
                mCardWidth = mCardGalleryWidth - AnimDisplayUtil.dip2px(mContext, 2 * (mPagePadding + mShowLeftCardWidth));
                mOnePageWidth = mCardWidth;
                mRecyclerView.smoothScrollToPosition(mCurrentItemPos);
                onScrolledChangedCallback();
            }
        });
    }

    public void setCurrentItemPos(int currentItemPos) {
        this.mCurrentItemPos = currentItemPos;
    }

    public int getCurrentItemPos() {
        return mCurrentItemPos;
    }

    private int getDestItemOffset(int destPos) {
        return mOnePageWidth * destPos;
    }

    /**
     * 计算mCurrentItemOffset
     */
    private void computeCurrentItemPos() {
        if (mOnePageWidth <= 0) return;
        boolean pageChanged = false;
        // 滑动超过一页说明已翻页
        if (Math.abs(mCurrentItemOffset - mCurrentItemPos * mOnePageWidth) >= mOnePageWidth) {
            pageChanged = true;
        }
        if (pageChanged) {
            int tempPos = mCurrentItemPos;

            mCurrentItemPos = mCurrentItemOffset / mOnePageWidth;
//            LogUtils.d(String.format("=======onCurrentItemPos Changed======= tempPos=%s, mCurrentItemPos=%s", tempPos, mCurrentItemPos));
        }
    }

    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    private void onScrolledChangedCallback() {

        int offset = mCurrentItemOffset - mCurrentItemPos * mOnePageWidth;
        float percent = (float) Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001);

//        LogUtils.d(String.format("offset=%s, percent=%s", offset, percent));
        View leftView = null;
        View currentView;
        View rightView = null;
        if (mCurrentItemPos > 0) {
            leftView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos - 1);
        }
        currentView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos);
        if (mCurrentItemPos < mRecyclerView.getAdapter().getItemCount() - 1) {
            rightView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos + 1);
        }

        if (leftView != null) {
            // y = (1 - mScale)x + mScale
//            leftView.setScaleY((1 - mScale) * percent + mScale);
            leftView.setAlpha((1 - mAlpha) * percent + mAlpha);
        }
        if (currentView != null) {
            // y = (mScale - 1)x + 1
//            currentView.setScaleY((mScale - 1) * percent + 1);
            currentView.setAlpha((mAlpha - 1) * percent + 1);
        }
        if (rightView != null) {
            // y = (1 - mScale)x + mScale
//            rightView.setScaleY((1 - mScale) * percent + mScale);
            rightView.setAlpha((1 - mAlpha) * percent + mAlpha);
        }
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }

    public interface CardScrolledLister {
        void ScrolledToPos(int pos);
    }

    public void setCardScrolledLister(CardScrolledLister l) {
        mCardScrolledLister = l;
    }
}
