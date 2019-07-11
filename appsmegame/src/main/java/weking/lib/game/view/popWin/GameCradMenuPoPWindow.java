package weking.lib.game.view.popWin;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import weking.lib.game.R;


/**
 * 创建时间 2017/7/6.
 * 创建人 frs
 * 功能描述
 */
public class GameCradMenuPoPWindow extends BasePopWinow implements View.OnClickListener {

    private GameCardMenuListener mGameCardMenuListener;

    public GameCradMenuPoPWindow(Context context) {
        super(context);
        mView.findViewById(R.id.winning_record).setOnClickListener(this);
        mView.findViewById(R.id.card_fapai).setOnClickListener(this);


        this.setAnimationStyle(R.style.game_crad_menu_Animation);

    }

    @Override
    protected int getResid() {
        return R.layout.game_crad_menu_pop;
    }

    @Override
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            mView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popHeight = mView.getMeasuredHeight();
            int popWidth = mView.getMeasuredWidth();
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            showAtLocation(parent, Gravity.NO_GRAVITY, location[0] - popWidth / 2 + parent.getWidth() / 2
                    , location[1] - popHeight);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        if (v.getId() == R.id.winning_record) {
            if (mGameCardMenuListener != null) {
                mGameCardMenuListener.OnClickWinningRecord();
            }
        } else if (v.getId() == R.id.card_fapai) {
            if (mGameCardMenuListener != null) {
                mGameCardMenuListener.OnClickCardFapai();
            }
        }
    }

    public interface GameCardMenuListener {
        void OnClickWinningRecord();

        void OnClickCardFapai();
    }

    public void setGameCardMenuListener(GameCardMenuListener l) {
        this.mGameCardMenuListener = l;
    }
}
