package weking.lib.game.event;

/**
 * Created by Administrator on 2017/12/28.
 */

public class CarPDEvent {

    private int times;

    public CarPDEvent(int times) {
        this.times = times;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
