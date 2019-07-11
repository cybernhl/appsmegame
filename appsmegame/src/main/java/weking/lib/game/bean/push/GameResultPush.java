package weking.lib.game.bean.push;

import java.util.List;

import app.io.weking.anim.bean.Gift;


public class GameResultPush extends BasePush {
    private int win_money;
    private int my_diamonds;
    private String account;
    private int game_id;

    private List<Gift> gift_info;
    //    public static final String win_money_position = "WD";
    // 输赢的钱
    private List<Integer> win_money_position;


    public List<Integer> getWin_money_position() {
        return win_money_position;
    }

    public void setWin_money_position(List<Integer> win_money_position) {
        this.win_money_position = win_money_position;
    }

    public List<Gift> getGift_info() {
        return this.gift_info;
    }

    public void setGift_info(List<Gift> gift_info) {
        this.gift_info = gift_info;
    }

    public int getWin_money() {
        return this.win_money;
    }

    public void setWin_money(int win_money) {
        this.win_money = win_money;
    }

    public int getMy_diamonds() {
        return this.my_diamonds;
    }

    public void setMy_diamonds(int my_diamonds) {
        this.my_diamonds = my_diamonds;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getGame_id() {
        return this.game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }
}


