package weking.lib.game.manager.memory;

import android.content.Context;

import weking.lib.game.R;

/**
 * Created by Administrator on 2016/12/6.
 */

public class GameSoundBg extends BaseGameSound {

    public static final int GAME_BG_DFJ_BG = 10021;//打飞机背景
    public static final int GAME_BG_ZWW_BG = 10022;//抓娃娃背景


    public static BaseGameSound getInstance() {
        if (mGameSoundBg == null) {
            mGameSoundBg = new GameSoundBg();
        }
        return mGameSoundBg;
    }

    public static BaseGameSound getNewInstance() {
        return mGameSoundBg;
    }

    public void playDfjBg(final Context context) {

        mGameSoundBg
                .setLoopNumber(-1)
                .setDivSsoundBet(5)
                .playSound(context, GameSoundBg.GAME_BG_DFJ_BG, R.raw.game_bg_dfj);

    }

    public void playZWWBg(final Context context) {

        mGameSoundBg
                .setLoopNumber(-1)
                .setDivSsoundBet(7)
                .playSound(context, GameSoundBg.GAME_BG_ZWW_BG, R.raw.game_bg_zww);


    }
}