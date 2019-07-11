package weking.lib.game.bean;

import java.util.List;

import weking.lib.rxretrofit.api.BaseResultEntity;

/**
 * 游戏历史数据 respond
 * Created by Administrator on 2016/12/24.
 */

public class GameHistoryRespond extends BaseResultEntity {
    private List<Integer> list;

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
