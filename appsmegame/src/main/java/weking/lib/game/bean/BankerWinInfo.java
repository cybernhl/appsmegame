package weking.lib.game.bean;

import java.util.ArrayList;

import weking.lib.rxretrofit.api.BaseResultEntity;

/**
 * 庄家流水
 * Created by Administrator on 2017/11/24.
 */

public class BankerWinInfo extends BaseResultEntity {
    private ArrayList<WinInfo> win_info;

    public ArrayList<WinInfo> getList() {
        return win_info;
    }

    public void setList(ArrayList<WinInfo> list) {
        this.win_info = list;
    }

    public static class WinInfo {
        //  赢的钱      "win_money": 10000,
        private int win_money;

        //  牌型   "card_type": 2
        private int card_type;

        public int getWin_money() {
            return win_money;
        }

        public void setWin_money(int win_money) {
            this.win_money = win_money;
        }

        public int getCard_type() {
            return card_type;
        }

        public void setCard_type(int card_type) {
            this.card_type = card_type;
        }
    }
}
