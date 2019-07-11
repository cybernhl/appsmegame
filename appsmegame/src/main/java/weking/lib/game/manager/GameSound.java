package weking.lib.game.manager;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import weking.lib.game.R;


/**
 * Created by Administrator on 2016/12/6.
 */

public class GameSound {

    public static final int SOUND_XIA_ZHU = 1;
    public static final int SOUND_WIN = 2;
    public static final int SOUND_WIN_GOLD = 3;
    public static final int SOUND_LOSE = 4;
    public static final int SOUND_FA_PAI = 5;

    SoundPool sp;
    HashMap<Integer, Integer> spMap;
    private Context mContext;


    public GameSound(Context context) {
        mContext = context;
    }

    public void initSoundPool() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC).build();
            sp = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(5).build();
        } else {
            //初始化声音池
            sp = new SoundPool(
                    5,              //maxStreams参数，该参数为设置同时能够播放多少音效
                    AudioManager.STREAM_MUSIC,  //streamType参数，该参数设置音频类型，在游戏中通常设置为：STREAM_MUSIC
                    0               //srcQuality参数，该参数设置音频文件的质量，目前还没有效果，设置为0为默认值。
            );
        }


        spMap = new HashMap<Integer, Integer>();
        spMap.put(SOUND_XIA_ZHU, sp.load(mContext, R.raw.game_tip_xiazhu, 1));
        spMap.put(SOUND_WIN, sp.load(mContext, R.raw.game_win, 1));
        spMap.put(SOUND_WIN_GOLD, sp.load(mContext, R.raw.game_win_gold, 1));
        spMap.put(SOUND_LOSE, sp.load(mContext, R.raw.game_lose, 1));
        spMap.put(SOUND_FA_PAI, sp.load(mContext, R.raw.game_fapai, 1));
    }

    public void playSound(int sound, int number) {    //播放声音,参数sound是播放音效的id，参数number是播放音效的次数
        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  //返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);//返回当前AudioManager对象的音量值
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
}
