package weking.lib.game.bean;

/**
 * Created by Administrator on 2018/1/3.
 */

public class VBean {

    public static final int SPEED_STATE_ACCELERATE = 2;
    public static final int SPEED_STATE_DECELERATE = 1;

    private int speed_state;

    private double time;

    private double start_time;

    public double getStart_time() {
        return start_time;
    }

    public void setStart_time(double start_time) {
        this.start_time = start_time;
    }

    public int getSpeed_state() {
        return speed_state;
    }

    public void setSpeed_state(int speed_state) {
        this.speed_state = speed_state;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
