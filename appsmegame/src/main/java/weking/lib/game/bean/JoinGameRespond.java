package weking.lib.game.bean;

import java.util.List;

import app.io.weking.anim.bean.Gift;

/**
 * 创建时间 2017/8/11.
 * 创建人 frs
 * 功能描述
 */
public class JoinGameRespond extends NextBenkerPush {


    public static final int STATE_XIA_ZHU = 2;
    public static final int STATE_KAI_PAI = 1;

    private int game_type;  // 游戏的type
    private int game_state;  // 进人的时机

    private int win_id;   //赢钱的礼物id
    private int win_money;    // 赢钱的礼物金钱

    private List<Gift> gift_info;
    private List<Poker> PG;  //牌

    private List<GameItemInfo> game_info;  //牌的数据
    private List<List<XiaZhuPush.GameBaen>> doll_game;  //抓娃娃的数据
    private List<XiaZhuPush.GameBaen> star_wars; //打飞机的数据


    public List<XiaZhuPush.GameBaen> getStar_wars() {
        return star_wars;
    }

    public void setStar_wars(List<XiaZhuPush.GameBaen> star_wars) {
        this.star_wars = star_wars;
    }

    public List<List<XiaZhuPush.GameBaen>> getDoll_game() {
        return doll_game;
    }

    public void setDoll_game(List<List<XiaZhuPush.GameBaen>> doll_game) {
        this.doll_game = doll_game;
    }

    public List<Poker> getPre_pokers() {
        return PG;
    }

    public void setPre_pokers(List<Poker> pre_pokers) {
        this.PG = pre_pokers;
    }

    public List<Gift> getGift_info() {
        return gift_info;
    }

    public void setGift_info(List<Gift> gift_info) {
        this.gift_info = gift_info;
    }


    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public int getGame_state() {
        return game_state;
    }

    public void setGame_state(int game_state) {
        this.game_state =  game_state;
    }

    public List<GameItemInfo> getGame_info() {
        return game_info;
    }

    public void setGame_info(List<GameItemInfo> game_info) {
        this.game_info = game_info;
    }

    public int getWin_id() {
        return win_id;
    }

    public void setWin_id(int win_id) {
        this.win_id = win_id;
    }

    public int getWin_money() {
        return win_money;
    }

    public void setWin_money(int win_money) {
        this.win_money = win_money;
    }
}

