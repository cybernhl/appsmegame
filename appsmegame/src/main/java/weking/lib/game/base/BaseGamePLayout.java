package weking.lib.game.base;

import android.content.Context;
import android.util.AttributeSet;

import weking.lib.game.presenter.IGameFactory;

/**
 * 创建时间 2017/8/14.
 * 创建人 frs
 * 功能描述
 */
public abstract class BaseGamePLayout  extends IGameFactory{


    public BaseGamePLayout(Context context) {
        super(context);
    }

    public BaseGamePLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
