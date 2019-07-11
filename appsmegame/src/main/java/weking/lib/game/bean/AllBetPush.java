package weking.lib.game.bean;

import java.util.List;

public class AllBetPush extends BaseGamePush {
    private List<BetInfo> bet_info;
    // 下注的倍数
    private int position_id;

    private int bet_number;

    private int account;

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public void setBet_number(int bet_number) {
        bet_number = bet_number;
    }

    public int getBet_number() {
        return this.bet_number;
    }

    public void setPosition_id(int position_id) {
        position_id = position_id;
    }

    public int getPosition_id() {
        return this.position_id;
    }

    public List<BetInfo> getBet_info() {
        return this.bet_info;
    }

    public void setBet_info(List<BetInfo> bet_info) {
        this.bet_info = bet_info;
    }

    public static class BetInfo {
        private int id;
        private int all_bet_number;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAll_bet_number() {
            return this.all_bet_number;
        }

        public void setAll_bet_number(int all_bet_number) {
            this.all_bet_number = all_bet_number;
        }
    }
}

