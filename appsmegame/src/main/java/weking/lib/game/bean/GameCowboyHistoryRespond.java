package weking.lib.game.bean;

import java.util.List;

import weking.lib.rxretrofit.api.BaseResultEntity;

/**
 * 游戏历史数据 respond
 * Created by Administrator on 2016/12/24.
 */

public class GameCowboyHistoryRespond extends BaseResultEntity {
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
