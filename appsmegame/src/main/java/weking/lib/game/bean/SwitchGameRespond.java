package weking.lib.game.bean;

import java.util.List;

/**
 * 创建时间 2017/8/14.
 * 创建人 frs
 * 功能描述  观众收到切换游戏的
 */
public class SwitchGameRespond {


    private int game_type;  // 游戏的type
    private List<List<XiaZhuPush.GameBaen>> doll_game;  //抓娃娃的数据
    private List<XiaZhuPush.GameBaen> star_wars; //打飞机的数据


    public List<XiaZhuPush.GameBaen> getStar_wars() {
        return star_wars;
    }

    public void setStar_wars(List<XiaZhuPush.GameBaen> star_wars) {
        this.star_wars = star_wars;
    }

    public List<List<XiaZhuPush.GameBaen>> getDoll_game() {
        return doll_game;
    }

    public void setDoll_game(List<List<XiaZhuPush.GameBaen>> doll_game) {
        this.doll_game = doll_game;
    }

    public int getGameType() {
        return game_type;
    }

    public void setGameType(int game_type) {
        this.game_type = game_type;
    }
}
