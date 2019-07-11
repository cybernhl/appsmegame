 package weking.lib.game.api;

 import retrofit2.Retrofit;
 import rx.Observable;
 import weking.lib.game.api.http.GameHttpPostService;

 public class GameFireApi
   extends GameBaseMidApi
 {
   private String access_token;
   private int live_id;
   private int id;
   private int capital;
   private boolean hit;

   public String getAccess_token()
   {
     return this.access_token;
   }

   public void setAccess_token(String access_token)
   {
     this.access_token = access_token;
   }

   public int getLive_id()
   {
     return this.live_id;
   }

   public void setLive_id(int live_id)
   {
     this.live_id = live_id;
   }

   public int getId()
   {
     return this.id;
   }

   public void setId(int id)
   {
     this.id = id;
   }

   public int getCapital()
   {
     return this.capital;
   }

   public void setCapital(int capital)
   {
     this.capital = capital;
   }

   public boolean isHit()
   {
     return this.hit;
   }

   public void setHit(boolean hit)
   {
     this.hit = hit;
   }

   public Observable getObservable(Retrofit retrofit)
   {
     GameHttpPostService service = (GameHttpPostService)retrofit.create(GameHttpPostService.class);
     return service.gameFire(this.access_token, this.live_id, this.hit, this.id, this.capital);
   }
 }

