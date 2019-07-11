package weking.lib.game.event;

import weking.lib.game.bean.CarDataPush;

/**
 * Created by Administrator on 2017/12/28.
 */

public class CarDataEvent {

    private CarDataPush carDataPush;

    public CarDataEvent(CarDataPush carDataPush) {
        this.carDataPush = carDataPush;
    }

    public CarDataPush getCarDataPush() {
        return carDataPush;
    }

    public void setCarDataPush(CarDataPush carDataPush) {
        this.carDataPush = carDataPush;
    }
}
