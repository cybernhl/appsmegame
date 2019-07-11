package weking.lib.game.event;

/**
 * Created by Administrator on 2017/12/26.
 */

public class CarShowAccelerateEvent {

    private float count ;

    private int car_id;

    private double time;

    private boolean isAccelerate;

    public CarShowAccelerateEvent(float count, int car_id, double time, boolean isAccelerate) {
        this.count = count;
        this.car_id = car_id;
        this.time = time;
        this.isAccelerate = isAccelerate;
    }

    public boolean getIsAccelerate() {
        return isAccelerate;
    }

    public void setIsAccelerate(boolean isAccelerate) {
        this.isAccelerate = isAccelerate;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }
}
