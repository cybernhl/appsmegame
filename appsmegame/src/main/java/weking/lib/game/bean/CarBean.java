package weking.lib.game.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/22.
 */

public class CarBean {

    // 距离第一名的距离
//    private float x;

    private List<Float> x = new ArrayList<Float>();

    // 当前速度
    private float v;
    // 加速道具数据
    private List<SpeedBean> car;
    // 每个时间点的加速数据 0 没有 1 加速 2 减速
    private List<VBean> time_list = new ArrayList<VBean>();

    private List<Float> speed_list = new ArrayList<Float>();




    public List<Float> getSpeed_list() {
        return speed_list;
    }

    public void setSpeed_list(List<Float> speed_list) {
        this.speed_list = speed_list;
    }

    public List<VBean> getTime_list() {
        return time_list;
    }

    public void setTime_list(List<VBean> time_list) {
        this.time_list = time_list;
    }

    public List<Float> getX() {
        return x;
    }

    public void setX(List<Float> x) {
        this.x = x;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public List<SpeedBean> getCar() {
        return car;
    }

    public void setCar(List<SpeedBean> car) {
        this.car = car;
    }
}
