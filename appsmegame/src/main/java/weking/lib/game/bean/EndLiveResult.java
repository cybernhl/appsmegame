package weking.lib.game.bean;

import java.util.List;

import weking.lib.rxretrofit.api.BaseResultEntity;

/**
 * Api Login返回的结果封装
 */
public class EndLiveResult extends BaseResultEntity {

    // 本地使用
    public boolean isAuthor;
    public boolean isFollow;
    // 本地使用

    private int IC;

    private String TF;
    // 本次打赏排行top3
    private List<Top3> CD;
    // 观看人数
    private int AF;
    // 转换成货币的本次收益
    private double GE;
    // 本次收入赤币
    private int TC;
    // 直播记录id
    private int LB;

    public boolean isAuthor() {
        return isAuthor;
    }

    public String getTotal_time() {
        return TF;
    }

    public List<Top3> getContribution_top3() {
        return CD;
    }

    public int getAudience_num() {
        return AF;
    }

    public double getGet_money() {
        return GE;
    }

    public int getTickets() {
        return TC;
    }

    public int getLive_id() {
        return LB;
    }

    public void setLive_id(int LB) {
        this.LB = LB;
    }

    public static class Top3 {
        private int SF;
        private String PB;

        public int getSend_tickets() {
            return SF;
        }

        public String getPic_head_low() {
            return PB;
        }
    }
}
