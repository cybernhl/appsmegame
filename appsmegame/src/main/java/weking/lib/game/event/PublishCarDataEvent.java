package weking.lib.game.event;

import java.util.List;

import weking.lib.game.bean.CarBean;

/**
 * Created by Administrator on 2018/3/2.
 */

public class PublishCarDataEvent {

    private List<CarBean> list;

    private int win_id;

    public PublishCarDataEvent(List<CarBean> list, int win_id) {
        this.list = list;
        this.win_id = win_id;
    }

    public int getWin_id() {
        return win_id;
    }

    public void setWin_id(int win_id) {
        this.win_id = win_id;
    }


    public List<CarBean> getList() {
        return list;
    }

    public void setList(List<CarBean> list) {
        this.list = list;
    }



}
