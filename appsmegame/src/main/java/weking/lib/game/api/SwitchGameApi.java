package weking.lib.game.api;

import retrofit2.Retrofit;
import rx.Observable;
import weking.lib.game.api.http.GameHttpPostService;

/**
 * 选择游戏
 */
public class SwitchGameApi
        extends GameBaseMidApi {
    private String access_token;
    private int live_id;
    private int game_type;

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getLive_id() {
        return this.live_id;
    }

    public void setLive_id(int live_id) {
        this.live_id = live_id;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        GameHttpPostService service = (GameHttpPostService) retrofit.create(GameHttpPostService.class);
        return service.switchGame(access_token, live_id, game_type);
    }
}

