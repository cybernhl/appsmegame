package weking.lib.game.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class GameManager {
    private int xiazhuMoney = 0;
    private boolean betLeft = false;
    private boolean betCenter = false;
    private boolean betRight = false;
    private static final int BAO_ZI = 6;
    private static final int TONG_HUA_SHUN = 5;
    private static final int TONG_HUA = 4;
    private static final int SHUN_ZI = 3;
    private static final int DUI_ZI = 2;
    private static final int DAN_PAI = 1;
    private static final int ONE_BIG = 1;
    private static final int ONE_SMALL = 0;
    static HashMap<Integer, String> card = new HashMap(0);
    static HashMap<Integer, String> color = new HashMap(0);
    static HashMap<Integer, String> types = new HashMap(0);
    static List<Poker> listPokers;

    static {
        card.put(Integer.valueOf(2), "2");
        card.put(Integer.valueOf(3), "3");
        card.put(Integer.valueOf(4), "4");
        card.put(Integer.valueOf(5), "5");
        card.put(Integer.valueOf(6), "6");
        card.put(Integer.valueOf(7), "7");
        card.put(Integer.valueOf(8), "8");
        card.put(Integer.valueOf(9), "9");
        card.put(Integer.valueOf(10), "10");
        card.put(Integer.valueOf(11), "J");
        card.put(Integer.valueOf(12), "Q");
        card.put(Integer.valueOf(13), "K");
        card.put(Integer.valueOf(14), "A");

        color.put(Integer.valueOf(1), "方块");
        color.put(Integer.valueOf(2), "梅花");
        color.put(Integer.valueOf(3), "红桃");
        color.put(Integer.valueOf(4), "黑桃");

        types.put(Integer.valueOf(1), "单牌");
        types.put(Integer.valueOf(2), "对子");
        types.put(Integer.valueOf(3), "顺子");
        types.put(Integer.valueOf(4), "同花");
        types.put(Integer.valueOf(5), "同花顺");
        types.put(Integer.valueOf(6), "豹子");


        listPokers = new ArrayList();
        for (int i = 2; i < 15; i++) {
            for (int j = 1; j < 5; j++) {
                Poker p = new Poker();
                p.card = i;
                p.color = j;
                listPokers.add(p);
            }
        }
    }


    public void test() {
        int[] pokers = getRandomSequence(52, 9);
        for (int j = 0; j < pokers.length; j++) {
            System.out.print((String) color.get(Integer.valueOf(((Poker) listPokers.get(pokers[j])).color)) +
                    (String) card.get(Integer.valueOf(((Poker) listPokers.get(pokers[j])).card)) + "-");
            if ((j + 1) % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        int[] left = new int[3];
        int[] middle = new int[3];
        int[] right = new int[3];
        System.arraycopy(pokers, 0, left, 0, 3);
        System.arraycopy(pokers, 3, middle, 0, 3);
        System.arraycopy(pokers, 6, right, 0, 3);

        int leftPokerType = analysisCardsType(left);
        int middlePokerType = analysisCardsType(middle);
        int rightPokerType = analysisCardsType(right);


        int result = compareTwoPlayer(leftPokerType, left, middlePokerType, middle);
        if (result == 1) {
            result = compareTwoPlayer(leftPokerType, left, rightPokerType, right);
            if (result == 1) {
                System.out.println("玩家1赢 " + (String) types.get(Integer.valueOf(leftPokerType)));
                System.out.println("玩家2 " + (String) types.get(Integer.valueOf(middlePokerType)));
                System.out.println("玩家3 " + (String) types.get(Integer.valueOf(rightPokerType)));
            } else {
                System.out.println("玩家1 " + (String) types.get(Integer.valueOf(leftPokerType)));
                System.out.println("玩家2 " + (String) types.get(Integer.valueOf(middlePokerType)));
                System.out.println("玩家3赢 " + (String) types.get(Integer.valueOf(rightPokerType)));
            }
        } else {
            result = compareTwoPlayer(middlePokerType, middle, rightPokerType, right);
            if (result == 1) {
                System.out.println("玩家1 " + (String) types.get(Integer.valueOf(leftPokerType)));
                System.out.println("玩家2赢 " + (String) types.get(Integer.valueOf(middlePokerType)));
                System.out.println("玩家3 " + (String) types.get(Integer.valueOf(rightPokerType)));
            } else {
                System.out.println("玩家1 " + (String) types.get(Integer.valueOf(leftPokerType)));
                System.out.println("玩家2 " + (String) types.get(Integer.valueOf(middlePokerType)));
                System.out.println("玩家3赢 " + (String) types.get(Integer.valueOf(rightPokerType)));
            }
        }
    }

    private int compareTwoPlayer(int oneType, int[] one, int twoType, int[] two) {
        if (oneType > twoType) {
            return 1;
        }
        if (oneType < twoType) {
            return 0;
        }
        Poker oneBig = (Poker) listPokers.get(one[2]);
        Poker twoBig = (Poker) listPokers.get(two[2]);
        switch (oneType) {
            case 6:
                return compareBaoZi(oneBig, twoBig);
            case 5:
                return compareTongHuaShun(oneBig, twoBig);
            case 4:
                return compareTongHua(oneBig, twoBig);
            case 3:
                return compareShunZi(oneBig, twoBig);
            case 2:
                return compareDuiZi(oneBig, twoBig);
            case 1:
                return compareDanPai(oneBig, twoBig);
        }
        return 1;
    }

    private int compareDanPai(Poker pokerOne, Poker pokerTwo) {
        if (pokerOne.card > pokerTwo.card) {
            return 1;
        }
        if (pokerOne.card < pokerTwo.card) {
            return 0;
        }
        return comparePokerColor(pokerOne, pokerTwo);
    }

    private int compareDuiZi(Poker pokerOne, Poker pokerTwo) {
        if (pokerOne.card > pokerTwo.card) {
            return 1;
        }
        if (pokerOne.card < pokerTwo.card) {
            return 0;
        }
        return comparePokerColor(pokerOne, pokerTwo);
    }

    private int compareShunZi(Poker pokerOne, Poker pokerTwo) {
        if (pokerOne.card > pokerTwo.card) {
            return 1;
        }
        if (pokerOne.card < pokerTwo.card) {
            return 0;
        }
        return comparePokerColor(pokerOne, pokerTwo);
    }

    private int compareTongHua(Poker pokerOne, Poker pokerTwo) {
        if (pokerOne.card > pokerTwo.card) {
            return 1;
        }
        return 0;
    }

    private int compareTongHuaShun(Poker pokerOne, Poker pokerTwo) {
        if (pokerOne.card > pokerTwo.card) {
            return 1;
        }
        if (pokerOne.card < pokerTwo.card) {
            return 0;
        }
        return comparePokerColor(pokerOne, pokerTwo);
    }

    private int compareBaoZi(Poker pokerOne, Poker pokerTwo) {
        if (pokerOne.card > pokerTwo.card) {
            return 1;
        }
        return 0;
    }

    private int comparePokerColor(Poker pokerOne, Poker pokerTwo) {
        if (pokerOne.color > pokerTwo.color) {
            return 1;
        }
        return 0;
    }

    private void sort3item(int[] pokers) {
        if (pokers[0] > pokers[1]) {
            int temp = pokers[0];
            pokers[0] = pokers[1];
            pokers[1] = temp;
        }
        if (pokers[0] > pokers[2]) {
            int temp = pokers[0];
            pokers[0] = pokers[2];
            pokers[2] = temp;
        }
        if (pokers[1] > pokers[2]) {
            int temp = pokers[1];
            pokers[1] = pokers[2];
            pokers[2] = temp;
        }
    }

    private int analysisCardsType(int[] pokers) {
        sort3item(pokers);

        Poker pokerSmall = (Poker) listPokers.get(pokers[0]);
        Poker pokerMiddle = (Poker) listPokers.get(pokers[1]);
        Poker pokerBig = (Poker) listPokers.get(pokers[2]);
        if ((pokerSmall.card == pokerMiddle.card) && (pokerSmall.card == pokerBig.card)) {
            return 6;
        }
        if ((pokerSmall.card == pokerMiddle.card) && (pokerSmall.card != pokerBig.card)) {
            return 2;
        }
        if ((pokerSmall.card == pokerBig.card) && (pokerSmall.card != pokerMiddle.card)) {
            return 2;
        }
        if ((pokerMiddle.card == pokerBig.card) && (pokerMiddle.card != pokerSmall.card)) {
            return 2;
        }
        if ((pokerSmall.card + 1 == pokerMiddle.card) && (pokerMiddle.card + 1 == pokerBig.card)) {
            if ((pokerSmall.color == pokerMiddle.color) && (pokerSmall.color == pokerBig.color)) {
                return 5;
            }
            return 3;
        }
        if ((pokerSmall.color == pokerMiddle.color) && (pokerSmall.color == pokerBig.color)) {
            return 4;
        }
        return 1;
    }

    public static int[] getRandomSequence(int total, int need) {
        int[] sequence = new int[total];
        int[] output = new int[need];
        for (int i = 0; i < total; i++) {
            sequence[i] = i;
        }
        Random random = new Random();

        int end = total - 1;
        for (int i = 0; i < need; i++) {
            int num = random.nextInt(end + 1);
            output[i] = sequence[num];
            sequence[num] = sequence[end];
            end--;
        }
        return output;
    }

    public void xiaZhu(int type, int id) {
        if (type == 0) {
            this.betLeft = true;
        } else if (type == 1) {
            this.betCenter = true;
        } else {
            this.betRight = true;
        }
        switch (id) {
            case 0:
                this.xiazhuMoney += 100;
                break;
            case 1:
                this.xiazhuMoney += 100;
                break;
            case 2:
                this.xiazhuMoney += 1000;
                break;
            case 3:
                this.xiazhuMoney += 5000;
        }
    }

    static class Poker {
        int card;
        int color;
    }
}
