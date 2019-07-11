package weking.lib.game.bean;

import java.util.List;

import app.io.weking.anim.bean.Gift;

/**
 * 观众加入游戏
 * Created by Administrator on 2016/9/20.
 */
public class EnterRespond extends EnterRoomResult {

    public static final int STATE_XIA_ZHU = 2;
    public static final int STATE_KAI_PAI = 1;

    private int CC;  //倒计时的时间

    private int GL;  // 进人的时机

    private int WB;   //赢钱的礼物id
    private int WA;    // 赢钱的礼物金钱

    private List<GameItemInfo> GB;  //牌的数据
    private List<Gift> GJ;
    private List<Poker> PG;  //牌
    private List<List<StartLiveResult.GameBaen>> DB;  //抓娃娃的数据
    private List<StartLiveResult.GameBaen> SG; //打飞机的数据



    public List<StartLiveResult.GameBaen> getStar_wars() {
        return SG;
    }

    public void setStar_wars(List<StartLiveResult.GameBaen> star_wars) {
        this.SG = star_wars;
    }

    public List<List<StartLiveResult.GameBaen>> getDoll_game() {
        return DB;
    }

    public void setDoll_game(List<List<StartLiveResult.GameBaen>> doll_game) {
        this.DB = doll_game;
    }

    public List<Poker> getPre_pokers() {
        return PG;
    }

    public void setPre_pokers(List<Poker> pre_pokers) {
        this.PG = pre_pokers;
    }

    public List<Gift> getGift_info() {
        return GJ;
    }

    public void setGift_info(List<Gift> gift_info) {
        this.GJ = gift_info;
    }

    public int getCountdown_time() {
        return CC;
    }

//    public int getGame_type() {
//        return GC;
//    }
//
//    public void setGame_type(int game_type) {
//        this.GC = game_type;
//    }

    public int getGame_state() {
        return GL;
    }

    public void setGame_state(int game_state) {
        this.GL = game_state;
    }

    public void setCountdown_time(int countdown_time) {
        this.CC = countdown_time;
    }

    public List<GameItemInfo> getGame_info() {
        return GB;
    }

    public void setGame_info(List<GameItemInfo> game_info) {
        this.GB = game_info;
    }

    public int getWin_id() {
        return WB;
    }

    public void setWin_id(int win_id) {
        this.WB = win_id;
    }

    public int getWin_money() {
        return WA;
    }

    public void setWin_money(int win_money) {
        this.WA= win_money;
    }
}
