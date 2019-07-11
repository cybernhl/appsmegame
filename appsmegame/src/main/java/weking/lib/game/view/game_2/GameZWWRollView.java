package weking.lib.game.view.game_2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import weking.lib.game.R;


/**
 * 创建时间 2017/8/9.
 * 创建人 frs
 * 功能描述
 */
public  class GameZWWRollView extends BaseGameZWWRollView {
    public GameZWWRollView(Context context) {
        super(context);
    }

    public GameZWWRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void findId(View view, GameRoolBean bean) {
        TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
        ImageView iv_bg = (ImageView) view.findViewById(R.id.iv_bg);
        ImageView iv_gift = (ImageView) view.findViewById(R.id.iv_gift);
        ImageView rl_head = (ImageView) view.findViewById(R.id.iv_head);
        bean.TV = tv_money;
        bean.HEAD = rl_head;
        bean.BG = iv_bg;
        bean.GIFT = iv_gift;
    }
}
