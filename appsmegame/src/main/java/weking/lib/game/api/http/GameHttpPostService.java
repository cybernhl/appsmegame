package weking.lib.game.api.http;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import weking.lib.game.bean.BanberListBean;
import weking.lib.game.bean.BankerWinInfo;
import weking.lib.game.bean.BaseGamePush;
import weking.lib.game.bean.BetRespond;
import weking.lib.game.bean.CrazyCarResult;
import weking.lib.game.bean.GameBetListRespond;
import weking.lib.game.bean.GameCowboyHistoryRespond;
import weking.lib.game.bean.GameFireRespond;
import weking.lib.game.bean.GameHistoryRespond;
import weking.lib.game.bean.GameKaiPaiPush;
import weking.lib.game.bean.JoinGameRespond;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.rxretrofit.api.BaseResultEntity;

public interface GameHttpPostService<T extends BaseGamePush> {
    @FormUrlEncoded
    @POST("game/bet")
    Observable<BetRespond> bet(@Field("access_token") String paramString, @Field("live_id") int paramInt1, @Field("position_id") int paramInt2, @Field("bet_num") int paramInt3);

    @FormUrlEncoded
    @POST("game/startGame")
    Observable<XiaZhuPush> startGame(@Field("access_token") String paramString, @Field("live_id") int paramInt1, @Field("game_type") int paramInt2);

    @FormUrlEncoded
    @POST("game/endGame")
    public abstract Observable<GameKaiPaiPush> endGame(
            @Field("access_token") String paramString,
            @Field("live_id") int paramInt1,
            @Field("game_id") int paramInt2,
            @Field("game_type") int game_type
    );

    @GET("game/gameList")
    Observable<GameHistoryRespond> gameHistory(@Query("access_token") String paramString, @Query("live_id") int paramInt);

    @GET("game/gameList")
    Observable<GameCowboyHistoryRespond> gameCowboyHistory(@Query("access_token") String paramString, @Query("live_id") int paramInt);

    @GET("game/getBetList")
    Observable<GameBetListRespond> getBetList(@Query("access_token") String paramString, @Query("live_id") int paramInt);

    @GET("game/fire")
    Observable<GameFireRespond> gameFire(@Query("access_token") String paramString, @Query("live_id") int paramInt1, @Query("hit") boolean paramBoolean, @Query("id") int paramInt2, @Query("capital") int paramInt3);

    @GET("game/join")
    Observable<JoinGameRespond> joinGame(@Query("access_token") String access_token, @Query("live_id") int live_id);

    //    切换游戏
    @GET("game/switch")
    Observable<BaseResultEntity> switchGame(
            @Query("access_token") String access_token,
            @Query("live_id") int live_id,
            @Query("game_type") int game_type
    );

    //    庄家列表
    @GET("game/bankerList")
    Observable<BanberListBean> banberList(
            @Query("access_token") String access_token,
            @Query("live_id") int live_id
    );

    //   下庄
    @GET("game/cancelBanker")
    Observable<BaseResultEntity> cancelBanker(
            @Query("access_token") String access_token,
            @Query("live_id") int live_id
    );

    //   上庄
    @GET("game/applyBanker")
    Observable<BaseResultEntity> applyBanker(
            @Query("access_token") String access_token,
            @Query("live_id") int live_id
    );

    //   庄家流水
    @GET("game/bankerWinInfo")
    Observable<BankerWinInfo> bankerWinInfo(
            @Query("access_token") String access_token,
            @Query("live_id") int live_id
    );

    //   赛车数据
    @GET("game/getGameResultData")
    Observable<CrazyCarResult> getCarResult(
            @Query("access_token") String access_token,
            @Query("live_id") int live_id
    );

}


