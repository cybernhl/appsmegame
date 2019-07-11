package weking.lib.game.bean;

import java.util.List;

import weking.lib.rxretrofit.api.BaseResultEntity;

/**
 * 创建时间 2017/5/10.
 * 创建人 frs
 * 功能描述
 */
public class GameBetListRespond extends BaseResultEntity {
  public List<GameBet> list;

  public class GameBet {

    public int bet_num;
    public String nickname;
    public String pic_head_low;
  }

}
