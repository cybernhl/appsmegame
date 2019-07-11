package weking.lib.game.bean;

import weking.lib.rxretrofit.api.BaseResultEntity;

/**
 * 下注返回 respond
 * Created by Administrator on 2016/12/24.
 */

public class BetRespond extends BaseResultEntity {
    private int position_id;
    private int my_bet_number;
    private int all_bet_number;
    private int my_diamonds;
    //自用
    private int currentBet;
    //自用


    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public int getMy_diamonds() {
        return my_diamonds;
    }

    public void setMy_diamonds(int my_diamonds) {
        this.my_diamonds = my_diamonds;
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
}
