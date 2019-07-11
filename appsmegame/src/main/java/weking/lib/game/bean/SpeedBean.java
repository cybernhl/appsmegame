package weking.lib.game.bean;

/**
 * Created by Administrator on 2017/12/22.
 */

public class SpeedBean {

    private int speed_grade;

    private int place_time;

    private boolean isShow = true;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int getSpeed_grade() {
        return speed_grade;
    }

    public void setSpeed_grade(int speed_grade) {
        this.speed_grade = speed_grade;
    }

    public int getPlace_time() {
        return place_time;
    }

    public void setPlace_time(int place_time) {
        this.place_time = place_time;
    }
}
