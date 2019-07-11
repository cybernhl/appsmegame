package weking.lib.game.event;

/**
 * Created by Administrator on 2017/12/22.
 */

public class CarSpeedGradeEvent {

    public static final int DEFAULT = 0;
    public static final int ACCELERATE = 1;
    public static final int DECELERATION = 2;

    //车辆id
    private int car_id;
    //车辆行驶距离
    private float speed_grade;
    //车辆所在的路基
    private float count;
    // 加速状态
    private int speed_state;

    public CarSpeedGradeEvent(int car_id, float speed_grade, float count) {
        this.car_id = car_id;
        this.speed_grade = speed_grade;
        this.count = count;
    }

    public int getSpeed_state() {
        return speed_state;
    }

    public void setSpeed_state(int speed_state) {
        this.speed_state = speed_state;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public float getSpeed_grade() {
        return speed_grade;
    }

    public void setSpeed_grade(float speed_grade) {
        this.speed_grade = speed_grade;
    }
}
