package weking.lib.game.event;

/**
 * 创建时间 2017/8/14.
 * 创建人 frs
 * 功能描述 点击toolbar 选择游戏
 */
public class ToolBarSwitchGameEvent {
    private  int gameType ;

    public ToolBarSwitchGameEvent(int gameType) {
        this.gameType = gameType;
    }

    public int getGameType() {
        return gameType;
    }
}
