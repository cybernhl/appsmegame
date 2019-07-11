package weking.lib.game.observer;

import weking.lib.utils.LogUtils;

public class GameObserver {
    public static GameObservable appObserver;

    public static void registerObserver(GameObservable observer) {
        appObserver = observer;
    }

    public static GameObservable getAppObserver() {
        if (appObserver == null) {
            LogUtils.e("GameObserver  appObserver  is  null  ");
            return null;
        }
        return appObserver;
    }
}



