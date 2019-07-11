package weking.lib.game.bean;

import weking.lib.rxretrofit.api.BaseResultEntity;

/**
 * Created by Administrator on 2017/6/21.
 */

public class GameFireRespond extends BaseResultEntity {
  public boolean hit;//  是否命中
  public int my_diamonds; //  我的剩余钻石
}
