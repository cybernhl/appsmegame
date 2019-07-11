package weking.lib.game.event;

/**
 * Created by Administrator on 2017/10/9.
 */

public class GameSetMoneyTextViewEvent {

    public int money;

    public GameSetMoneyTextViewEvent(int my_diamonds) {
        this.money = my_diamonds;
    }
}
