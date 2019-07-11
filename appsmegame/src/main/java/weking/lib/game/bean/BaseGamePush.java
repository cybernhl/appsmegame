 package weking.lib.game.bean;

 import weking.lib.rxretrofit.api.BaseResultEntity;

 public class BaseGamePush  extends BaseResultEntity
 {
   private int countdown_time;
   private int GA;
   public static final String IM_CODE = "im_code";
   private int IC;

   public int getIm_code()
   {
     return this.IC;
   }

   public void setIm_code(int im_code)
   {
     this.IC = im_code;
   }

   public int getGame_id()
   {
     return this.GA;
   }

   public void setGame_id(int game_id)
   {
     this.GA = game_id;
   }

   public int getCountdown_time()
   {
     return this.countdown_time;
   }

   public void setCountdown_time(int countdown_time)
   {
     this.countdown_time = countdown_time;
   }
 }
