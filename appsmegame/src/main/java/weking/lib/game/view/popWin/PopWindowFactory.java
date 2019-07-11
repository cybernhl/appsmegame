package weking.lib.game.view.popWin;

import android.widget.PopupWindow;

import weking.lib.game.observer.GameObserver;

/**
 * 创建时间 2017/8/23.
 * 创建人 frs
 * 功能描述 popwin 的工厂类
 */

public class PopWindowFactory<T extends PopupWindow> {
    public PopWindowFactory() {
        T t = (T) new PopupWindow(GameObserver.getAppObserver().getCurrentActivity());
    }
}
