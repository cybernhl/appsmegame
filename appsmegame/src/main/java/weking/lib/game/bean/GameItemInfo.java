package weking.lib.game.bean;

import weking.lib.game.bean.base.BasePokerInfo;

/**
 * Created by Administrator on 2016/12/24.
 */

public class GameItemInfo extends BasePokerInfo {

    private int position_id;
    private int my_bet_number;
    private int all_bet_number;
    private int poker_type;
    private int WC; // 1 赢钱 ,2输钱
    public int getWin() {
        return WC;
    }

    public void setWin(int WC) {
        this.WC = WC;
    }
    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public int getMy_bet_number() {
        return my_bet_number;
    }

    public void setMy_bet_number(int my_bet_number) {
        this.my_bet_number = my_bet_number;
    }

    public int getAll_bet_number() {
        return all_bet_number;
    }

    public void setAll_bet_number(int all_bet_number) {
        this.all_bet_number = all_bet_number;
    }

    public int getPoker_type() {
        return poker_type;
    }

    public void setPoker_type(int poker_type) {
        this.poker_type = poker_type;
    }
}


