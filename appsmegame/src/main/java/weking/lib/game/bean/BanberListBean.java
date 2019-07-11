package weking.lib.game.bean;

import java.util.List;

import weking.lib.rxretrofit.api.BaseResultEntity;

/**
 * 创建时间 2017/6/21.
 * 创建人 frs
 * 功能描述
 */
public class BanberListBean extends BaseResultEntity{
    private List<BanberBean> list;

    public List<BanberBean> getBanberList() {
        return list;
    }

    public void setBanberList(List<BanberBean> banberList) {
        this.list = banberList;
    }

    public static class BanberBean {
        private int total_diamond;
        private String nickname;
        //自用
        private String order;
        private boolean isTitle;
        //自用

        public BanberBean( boolean isTitle) {

            this.isTitle = isTitle;
        }

        public boolean isTitle() {
            return isTitle;
        }

        public void setTitle(boolean title) {
            isTitle = title;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public int getTotal_diamond() {
            return total_diamond;
        }

        public void setTotal_diamond(int total_diamond) {
            this.total_diamond = total_diamond;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
