package weking.lib.game.api;


import retrofit2.Retrofit;
import rx.Observable;
import weking.lib.game.api.http.GameHttpPostService;

/**
 * Created by Administrator on 2017/12/28.
 */

public class CrazyCarDataApi extends GameBaseMidApi{

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
        GameHttpPostService gameHttpPostService = retrofit.create(GameHttpPostService.class);
        return gameHttpPostService.getCarResult(access_token,live_id);
    }

}
