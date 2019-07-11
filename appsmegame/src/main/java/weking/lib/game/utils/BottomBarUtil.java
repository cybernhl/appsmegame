
package weking.lib.game.utils;

import android.widget.ImageView;

import weking.lib.game.R;
import weking.lib.game.base.BaseGameLayout;

public class BottomBarUtil {

    public BottomBarUtil() {
    }

    public static int setBetImage(ImageView IV_bet10, ImageView IV_bet100, ImageView IV_bet1000, ImageView IV_bet5000, int money, int bet) {

        // 当前选择的筹码值
        int currentBet = bet;
        if (money < BaseGameLayout.BET_10) {
            IV_bet10.setImageResource(R.drawable.game_bet_base_no);
            IV_bet100.setImageResource(R.drawable.game_bet_base_no);
            IV_bet1000.setImageResource(R.drawable.game_bet_base_no);
            IV_bet5000.setImageResource(R.drawable.game_bet_base_no);


        } else if (money < BaseGameLayout. BET_100) {
            currentBet =  BaseGameLayout.BET_10;
            IV_bet10.setImageResource(R.drawable.game_bet_10);
            IV_bet100.setImageResource(R.drawable.game_bet_base_no);
            IV_bet1000.setImageResource(R.drawable.game_bet_base_no);
            IV_bet5000.setImageResource(R.drawable.game_bet_base_no);


        } else if (money <  BaseGameLayout.BET_1000) {
            IV_bet10.setImageResource(R.drawable.game_bet_10_nor);
            IV_bet100.setImageResource(R.drawable.game_bet_100_nor);
            IV_bet1000.setImageResource(R.drawable.game_bet_base_no);
            IV_bet5000.setImageResource(R.drawable.game_bet_base_no);


            if (currentBet == BaseGameLayout. BET_100) {
                IV_bet100.setImageResource(R.drawable.game_bet_100);
            } else {
                currentBet =  BaseGameLayout.BET_10;
                IV_bet10.setImageResource(R.drawable.game_bet_10);
            }

        } else if (money <  BaseGameLayout.BET_5000) {
            IV_bet10.setImageResource(R.drawable.game_bet_10_nor);
            IV_bet100.setImageResource(R.drawable.game_bet_100_nor);
            IV_bet1000.setImageResource(R.drawable.game_bet_1k_nor);
            IV_bet5000.setImageResource(R.drawable.game_bet_base_no);

            if (currentBet ==  BaseGameLayout.BET_1000) {
                IV_bet1000.setImageResource(R.drawable.game_bet_1k);
            } else if (currentBet == BaseGameLayout. BET_100) {
                IV_bet100.setImageResource(R.drawable.game_bet_100);
            } else {
                currentBet = BaseGameLayout. BET_10;
                IV_bet10.setImageResource(R.drawable.game_bet_10);

            }

        } else {
            IV_bet10.setImageResource(R.drawable.game_bet_10_nor);
            IV_bet100.setImageResource(R.drawable.game_bet_100_nor);
            IV_bet1000.setImageResource(R.drawable.game_bet_1k_nor);
            IV_bet5000.setImageResource(R.drawable.game_bet_5k_nor);


            if (currentBet == BaseGameLayout. BET_5000) {
                IV_bet5000.setImageResource(R.drawable.game_bet_5k);

            } else if (currentBet == BaseGameLayout. BET_1000) {
                IV_bet1000.setImageResource(R.drawable.game_bet_1k);
            } else if (currentBet ==  BaseGameLayout.BET_100) {
                IV_bet100.setImageResource(R.drawable.game_bet_100);
            } else {
                currentBet = BaseGameLayout. BET_10;
                IV_bet10.setImageResource(R.drawable.game_bet_10);
            }
        }
        return currentBet;
    }
}
