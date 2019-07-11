package weking.lib.game.event;

/**
 * Created by Administrator on 2017/8/14.
 */

public class GameStartEvent {

    int game_type;

    public GameStartEvent(int game_type) {
        this.game_type = game_type;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }
}
