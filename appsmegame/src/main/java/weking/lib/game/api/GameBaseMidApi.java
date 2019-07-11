package weking.lib.game.api;

import weking.lib.game.GameC;
import weking.lib.game.observer.GameObserver;
import weking.lib.rxretrofit.api.BaseApi;

public abstract class GameBaseMidApi
        extends BaseApi {
    public GameBaseMidApi() {
        String BASE_URL = GameObserver.getAppObserver().getStringText(GameC.str.BASE_URL);
        double API_VERSION = ((Double) GameObserver.getAppObserver().getObjectText(GameC.str.API_VERSION, Double.valueOf(0.1f))).doubleValue();
        setBaseUrl(BASE_URL);
        setApi_version(API_VERSION);
        setShowProgress(true);
        setCancel(true);
    }
}

