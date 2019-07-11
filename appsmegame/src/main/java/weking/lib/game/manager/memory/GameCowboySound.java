package weking.lib.game.manager.memory;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import weking.lib.game.R;

/**
 * 创建时间 2017/10/25.
 * 创建人 frs
 * 功能描述 牛仔的音乐
 */
public class GameCowboySound {

    public static int GAME_COWBOY_TIME_COUNTDOWN = 1;// 时间到了
    public static int GAME_COWBOY_SHUFFLE = 2;// 洗牌
    public static int GAME_COWBOY_CHIP = 3;// 下注
    public static int GAME_COWBOY_WIN_MONEY = 4;//  win money

    private static GameCowboySound mGameSoundBg;


    SoundPool sp;
    HashMap<Integer, Integer> spMap;

    public static GameCowboySound getInstance() {

        if (mGameSoundBg == null) {
            mGameSoundBg = new GameCowboySound();
        }
        return mGameSoundBg;
    }

    public void initSoundPool(Context context, boolean isPublish) {
        if (isPublish) {
            return;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC).build();
            sp = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(5).build();
        } else {
            //初始化声音池
            sp = new SoundPool(
                    5,              //maxStreams参数，该参数为设置同时能够播放多少音效
                    AudioManager.STREAM_RING,  //streamType参数，该参数设置音频类型，在游戏中通常设置为：STREAM_MUSIC
                    0               //srcQuality参数，该参数设置音频文件的质量，目前还没有效果，设置为0为默认值。
            );
        }
        spMap = new HashMap<Integer, Integer>();

        spMap.put(GAME_COWBOY_SHUFFLE, sp.load(context, R.raw.game_cowboy_shuffle, 1));
        spMap.put(GAME_COWBOY_TIME_COUNTDOWN, sp.load(context, R.raw.game_cowboy_time_countdown, 1));
        spMap.put(GAME_COWBOY_CHIP, sp.load(context, R.raw.game_cowboy_chip, 1));
        spMap.put(GAME_COWBOY_WIN_MONEY, sp.load(context, R.raw.game_cowboy_win_money, 1));
    }


    public void playSound(Context context, int sound, int number, boolean isPublish) {    //播放声音,参数sound是播放音效的id，参数number是播放音效的次数
        if (isPublish) {
            return;
        }
//        nowPlaySound = sound;
        //实例化AudioManager对象
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_RING);  //返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_RING);//返回当前AudioManager对象的音量值
        float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        sp.play(
                (int) spMap.get(sound),                   //播放的音乐id
                volumnRatio,                        //左声道音量
                volumnRatio,                        //右声道音量
                1,                                  //优先级，0为最低
                number,                             //循环次数，0 不循环，-1 永远循环
                1                                   //回放速度 ，该值在0.5-2.0之间，1为正常速度
        );
    }

    public void stopSound(int sound) {
        boolean isContains = spMap.containsKey(sound);
        if (isContains) {
            if (sp != null && spMap != null) {
                sp.autoPause();
//                sp.stop(spMap.get(sound));
            }
        }
    }

    public void soundDestroy(boolean isPublish) {
        if (isPublish) {
            return;
        }
        if (sp != null) {
            sp.release();
            spMap.clear();
            sp = null;

        }
        mGameSoundBg = null;
    }
}
