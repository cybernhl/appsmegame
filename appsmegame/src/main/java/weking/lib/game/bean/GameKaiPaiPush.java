package weking.lib.game.bean;

import java.util.List;

/**
 * 推送的GAME信息
 * <p>
 * Created by zhengb on 2016/9/2.
 */
public class GameKaiPaiPush extends BaseGamePush {

    private int win_id;  //赢得那桌
    private int live_id;
    private int anchor_tickets;   // 主播的钱
    private int switch_type;


    private int BF;
    //庄家的钱
    private int BI;

    public int getSwitch_type() {
        return switch_type;
    }

    public void setSwitch_type(int switch_type) {
        this.switch_type = switch_type;
    }

    public int getBanker_diamond() {
        return BI;
    }

    public void setBanker_diamond(int banker_diamond) {
        this.BI = banker_diamond;
    }

    private List<GameItemInfo> GB;

    public List<GameItemInfo> getGame_info() {
        return GB;
    }

    public void setGame_info(List<GameItemInfo> game_info) {
        this.GB = game_info;
    }

    public int getWin_id() {
        return win_id;
    }

    public void setWin_id(int win_id) {
        this.win_id = win_id;
    }

    public int getLive_id() {
        return live_id;
    }

    public int getAnchor_tickets() {
        return anchor_tickets;
    }

    public void setAnchor_tickets(int anchor_tickets) {
        this.anchor_tickets = anchor_tickets;
    }

    public void setLive_id(int live_id) {
        this.live_id = live_id;
    }

    public int getBanker_win_num() {
        return BF;
    }

    public void setBanker_win_num(int banker_win_num) {
        this.BF = banker_win_num;
    }
}
