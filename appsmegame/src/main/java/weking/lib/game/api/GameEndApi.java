 package weking.lib.game.api;

 import retrofit2.Retrofit;
 import rx.Observable;
 import weking.lib.game.api.http.GameHttpPostService;

 public class GameEndApi
   extends GameBaseMidApi
 {
   private String access_token;
   private int live_id;
   private int game_id;
     private int game_type;

   public String getAccess_token()
   {
     return this.access_token;
   }

   public void setAccess_token(String access_token)
   {
     this.access_token = access_token;
   }

   public void setLive_id(int live_id)
   {
     this.live_id = live_id;
   }

   public void setGame_id(int game_id)
   {
     this.game_id = game_id;
   }

   public void setGame_type(int game_type) {
     this.game_type = game_type;
   }

   public Observable getObservable(Retrofit retrofit)
   {
     GameHttpPostService service = (GameHttpPostService)retrofit.create(GameHttpPostService.class);
     return service.endGame(this.access_token, this.live_id, this.game_id,game_type);
   }
 }


