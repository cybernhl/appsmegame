package weking.lib.game.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */

public class CarDataPush {

    private int win_id;

    private List<CarBean> speed_data;

    private int im_code;

    public int getWin_id() {
        return win_id;
    }

    public void setWin_id(int win_id) {
        this.win_id = win_id;
    }

    public  List<CarBean> getSpeed_data() {
        return speed_data;
    }

    public void setSpeed_data( List<CarBean> speed_data) {
        this.speed_data = speed_data;
    }

    public int getIm_code() {
        return im_code;
    }

    public void setIm_code(int im_code) {
        this.im_code = im_code;
    }
}
