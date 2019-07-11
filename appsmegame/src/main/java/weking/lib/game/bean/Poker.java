package weking.lib.game.bean;

/**
 * Created by Administrator on 2016/12/24.
 */

public class Poker {
    // 1.单牌  2.对子  3.顺子  4.同花  5.同花顺 6.豹子
    public static final int ZJH_DAN_PAI = 1;
    public static final int ZJH_DUI_ZI = 2;
    public static final int ZJH_SHUN_ZI = 3;
    public static final int ZJH_TONG_HUA = 4;
    public static final int ZJH_TONG_HUA_SHUN = 5;
    public static final int ZJH_BAO_ZI = 6;


    public static final int DZ_GAO_PAI = 1;
    public static final int DZ_YI_DUI = 2;
    public static final int DZ_LIANG_DUI = 3;
    public static final int DZ_SAN_TIAO = 4;
    public static final int DZ_SHUN_ZI = 5;
    public static final int DZ_TONG_HUA = 6;
    public static final int DZ_HU_LU = 7;
    public static final int DZ_SI_TIAO = 8;
    public static final int DZ_TONG_HUA_SHUN = 9;
    public static final int DZ_HJTHS = 10;

    // 0没牛，1牛一，2牛二，3牛三，4牛四，5牛五，6牛七，7牛七，8牛八，9牛九，10牛牛，11四花，12五花，13五小花，14四炸

    public static final int NN_NIU0 = 0;
    public static final int NN_NIU1 = 1;
    public static final int NN_NIU2 = 2;
    public static final int NN_NIU3 = 3;
    public static final int NN_NIU4 = 4;
    public static final int NN_NIU5 = 5;
    public static final int NN_NIU6 = 6;
    public static final int NN_NIU7 = 7;
    public static final int NN_NIU8 = 8;
    public static final int NN_NIU9 = 9;
    public static final int NN_NIU10 = 10;
    public static final int NN_HUA4 = 11;
    public static final int NN_HUA5BIG = 12;
    public static final int NN_HUA5SMALL = 13;
    public static final int NN_ZD = 14;

    // 1.方块 2.梅花  3.红桃  4.黑桃
    public static final int FANG_KUAI = 1;
    public static final int MEI_HUA = 2;
    public static final int HONG_TAO = 3;
    public static final int HEI_TAO = 4;

    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int J = 11;
    public static final int Q = 12;
    public static final int K = 13;
    public static final int A = 14;


    private int NC;
    private int CB;

    public int getColor() {
        return CB;
    }

    public void setColor(int color) {
        this.CB = color;
    }

    public int getNumber() {

        return NC;
    }

    public void setNumber(int number) {
        this.NC = number;
    }
}
