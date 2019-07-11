package weking.lib.game.api;

import retrofit2.Retrofit;
import rx.Observable;
import weking.lib.game.api.http.GameHttpPostService;

/**
 * 下注
 */
public class CancelBankerApi
        extends GameBaseMidApi {
    private String access_token;
    private int live_id;


    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setLive_id(int live_id) {
        this.live_id = live_id;
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        GameHttpPostService httpService = (GameHttpPostService) retrofit.create(GameHttpPostService.class);
        return httpService.cancelBanker(this.access_token, this.live_id);
    }
}


