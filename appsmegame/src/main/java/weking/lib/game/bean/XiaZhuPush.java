package weking.lib.game.bean;

import java.util.List;

public class XiaZhuPush
        extends BaseGamePush {
    List<Poker> pre_pokers;
    public List<List<GameBaen>> doll_game;
    public List<GameBaen> star_wars;

    public List<Poker> getPre_pokers() {
        return this.pre_pokers;
    }

    public void setPre_pokers(List<Poker> pre_pokers) {
        this.pre_pokers = pre_pokers;
    }

    public List<Poker> getPG() {
        return this.pre_pokers;
    }

    public void setPG(List<Poker> PG) {
        this.pre_pokers = PG;
    }

    public List<List<GameBaen>> getDoll_game() {
        return this.doll_game;
    }

    public List<GameBaen> getStar_wars() {
        return this.star_wars;
    }

    public void setDoll_game(List<List<GameBaen>> doll_game) {
        this.doll_game = doll_game;
    }

    public void setStar_wars(List<GameBaen> star_wars) {
        this.star_wars = star_wars;
    }

    public class GameBaen {
        private int id;
        public float radix;

        public GameBaen() {
        }

        public float getRadix() {
            return this.radix;
        }

        public void setRadix(float radix) {
            this.radix = radix;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}

