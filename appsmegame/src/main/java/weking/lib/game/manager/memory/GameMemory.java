package weking.lib.game.manager.memory;

import java.util.List;

/**
 * 创建时间 2017/8/15.
 * 创建人 frs
 * 功能描述
 */
public class GameMemory {
    public static int nowGameType;//当前的游戏
    //选择游戏的列表
    private static List<Integer> mSwitchGameList;

    public static void setSwitchGameList(List<Integer> switchGameList) {
        mSwitchGameList = switchGameList;
    }

    public static List<Integer> getmSwitchGameList() {
        return mSwitchGameList;
    }

    //清楚游戏 的列表
    public static void removeSwitchGameList() {
        mSwitchGameList = null;
    }
}
