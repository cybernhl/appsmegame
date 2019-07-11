package weking.lib.game.bean;

import java.util.List;

import weking.lib.rxretrofit.api.BaseResultEntity;

/**
 * Created by Administrator on 2017/12/22.
 */

public class CrazyCarResult extends BaseResultEntity{


    private List<CarBean> speed_data;

    private int win_id;

    public int getWin_id() {
        return win_id;
    }

    public void setWin_id(int win_id) {
        this.win_id = win_id;
    }

    public List<CarBean> getSpeed_data() {
        return speed_data;
    }

    public void setSpeed_data(List<CarBean> speed_data) {
        this.speed_data = speed_data;
    }
}
