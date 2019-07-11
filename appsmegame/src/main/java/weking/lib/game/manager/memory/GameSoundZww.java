package weking.lib.game.manager.memory;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import weking.lib.game.R;


/**
 * Created by Administrator on 2016/12/6.
 */

public class GameSoundZww {

    public static final int SOUND_WIN_GOLD = 3;//赢钱
    public static final int GAME_ZWW_DOWN = 4;//抓娃娃下勾的声音
    public static final int GAME_ZWW_ERROR = 5;//抓娃娃下勾失败的声音
    public static final int GAME_ZWW_SUCCEED = 6;//抓娃娃下勾成功的声音
    public static final int GAME_DFJ_FIRE = 7;//打飞机开炮
    private Context mContext;


    SoundPool sp;
    HashMap<Integer, Integer> spMap;

    public GameSoundZww(Context context) {
        this.mContext = context;
    }

    public void initSoundPool() {
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
        spMap.put(SOUND_WIN_GOLD, sp.load(mContext, R.raw.game_win_gold, 1));
        spMap.put(GAME_ZWW_DOWN, sp.load(mContext, R.raw.game_zww_down, 1));
        spMap.put(GAME_ZWW_ERROR, sp.load(mContext, R.raw.game_zww_error, 1));
        spMap.put(GAME_ZWW_SUCCEED, sp.load(mContext, R.raw.game_zww_succeed, 1));
        spMap.put(GAME_DFJ_FIRE, sp.load(mContext, R.raw.game_dfj_fire, 1));
    }


    public void playSound(int sound, int number) {
        playSound(sound, number, 1);
    }

    public void playSound(int sound, int number, int soundBet) {    //播放声音,参数sound是播放音效的id，参数number是播放音效的次数


        //实例化AudioManager对象
        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_RING);  //返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_RING);//返回当前AudioManager对象的音量值
        float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        sp.play(
                (int) spMap.get(sound),                   //播放的音乐id
                volumnRatio / soundBet,                        //左声道音量
                volumnRatio / soundBet,                        //右声道音量
                1,                                  //优先级，0为最低
                number,                             //循环次数，0 不循环，-1 永远循环
                1                                   //回放速度 ，该值在0.5-2.0之间，1为正常速度
        );
    }

    public void stopSound(int sound) {
        boolean isContains = spMap.containsKey(sound);
        if (isContains) {
            if (sp != null && spMap != null) {
                sp.stop(spMap.get(sound));
            }
        }
    }

    public void soundDestroy() {
        if (sp != null) {
            sp.release();
        }
    }
}
