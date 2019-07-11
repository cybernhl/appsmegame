package weking.lib.game.bean.base;

import java.util.List;

import weking.lib.game.bean.Poker;

/**
 * 棋牌数据的base
 */

public class BasePokerInfo {

    private List<Poker> PD;

    public List<Poker> getPoker_info() {
        return PD;
    }

    public void setPoker_info(List<Poker> poker_info) {
        this.PD = poker_info;
    }
}
