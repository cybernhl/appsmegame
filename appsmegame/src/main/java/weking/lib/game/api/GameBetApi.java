 package weking.lib.game.api;

 import retrofit2.Retrofit;
 import rx.Observable;
 import weking.lib.game.api.http.GameHttpPostService;

 public class GameBetApi
   extends GameBaseMidApi
 {
   private String access_token;
   private int live_id;
   private int position_id;
   private int bet_num;

   public void setAccess_token(String access_token)
   {
     this.access_token = access_token;
   }

   public void setLive_id(int live_id)
   {
     this.live_id = live_id;
   }

   public void setPosition_id(int position_id)
   {
     this.position_id = position_id;
   }

   public void setBet_num(int bet_num)
   {
     this.bet_num = bet_num;
   }

   @Override
   public Observable getObservable(Retrofit retrofit)
   {
     GameHttpPostService httpService = (GameHttpPostService)retrofit.create(GameHttpPostService.class);
     return httpService.bet(this.access_token, this.live_id, this.position_id, this.bet_num);
   }
 }


