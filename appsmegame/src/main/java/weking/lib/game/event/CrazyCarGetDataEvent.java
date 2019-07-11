package weking.lib.game.event;

import java.util.List;

import weking.lib.game.bean.CarBean;

/**
 * Created by Administrator on 2018/1/3.
 */

public class CrazyCarGetDataEvent {

    private List<CarBean> carBeanList;

    public CrazyCarGetDataEvent(List<CarBean> carBeanList) {
        this.carBeanList = carBeanList;
    }

    public List<CarBean> getCarBeanList() {
        return carBeanList;
    }

    public void setCarBeanList(List<CarBean> carBeanList) {
        this.carBeanList = carBeanList;
    }
}
