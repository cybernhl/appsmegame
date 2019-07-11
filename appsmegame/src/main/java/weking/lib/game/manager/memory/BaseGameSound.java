package weking.lib.game.manager.memory;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import weking.lib.game.utils.GameAction;

/**
 * 创建时间 2017/9/22.
 * 创建人 frs
 * 功能描述
 */
public class BaseGameSound {

    protected static int nowPlaySound = 0;//当前的声音

    protected static BaseGameSound mGameSoundBg;
    private GameAction.OnResult<Integer> action;  //回调地 action
    private boolean isPlaySound = true;  // 是否自动播放
    private int divSsoundBet = 1;   // 设置声音倍数 除法
    private int loopNumber; //循环次数，0 不循环，-1 永远循环
    private SoundPool sp;
    private HashMap<Integer, Integer> spMap;


    /**
     * 播放声音
     *
     * @param context
     * @param soundKey   声音的key
     * @param soundValue 声音的 value
     */

    public void playSound(final Context context, final int soundKey, final int soundValue) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (sp == null) {
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
                }

                sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        if (spMap == null || spMap.size() == 0 || sp == null) {
                            if (action != null) {
                                action.onError();
                            }
                        } else {
                            if (action != null) {
                                action.invoke(soundKey);
                            }
                            if (isPlaySound) {
                                startPlaySound(context, soundKey, loopNumber, divSsoundBet);
                            }
                        }
                    }
                });

                if (spMap == null || spMap.size() == 0) {
                    spMap = new HashMap<Integer, Integer>();
                }
                Integer integer = spMap.get(soundKey);
                if (integer != null && integer != 0) { // 判断有没有初始化
                    startPlaySound(context, soundKey, loopNumber, divSsoundBet);
                } else {
                    spMap.put(soundKey, sp.load(context, soundValue, 1));
                }
            }
        }).start();
    }

    AudioManager am;
    float volumnRatio;

    private void startPlaySound(Context context, int sound, int loopNumber, int soundBet) {    //播放声音,参数sound是播放音效的id，参数number是播放音效的次数
        nowPlaySound = sound;
        //实例化AudioManager对象
        if (am == null) {
            am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_RING);  //返回当前AudioManager对象的最大音量值
            float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_RING);//返回当前AudioManager对象的音量值
            volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        }
        if (spMap == null || spMap.size() == 0 || sp == null) {
            return;
        }
        sp.play(
                (int) spMap.get(sound),                   //播放的音乐id
                volumnRatio / soundBet,                        //左声道音量
                volumnRatio / soundBet,                           //右声道音量
                1,                                  //优先级，0为最低
                loopNumber,                             //循环次数，0 不循环，-1 永远循环
                1                                   //回放速度 ，该值在0.5-2.0之间，1为正常速度
        );
    }

    public void stopSound() {
        if (spMap != null) {
            boolean isContains = spMap.containsKey(nowPlaySound);
            if (isContains) {
                if (sp != null && spMap != null) {
                    sp.autoPause();
                }
            }
        }
    }

//    public void startSound() {
//        boolean isContains = spMap.containsKey(nowPlaySound);
//        if (isContains) {
//            if (sp != null && spMap != null) {
//                sp.resume(nowPlaySound);
//            }
//        }
//    }

    public void soundDestroy(final GameAction.OnResult onResult) {
        if (sp != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    sp.unload(nowPlaySound);
                    sp.release();
                    if (sp != null) {
                        onResult.invoke(null);
                    }
                    mGameSoundBg = null;
                    spMap.clear();
                    sp = null;
                }
            }).start();
        }
    }


    protected BaseGameSound() {
    }


    public BaseGameSound setAction(GameAction.OnResult<Integer> action) {
        this.action = action;
        return mGameSoundBg;
    }

    public BaseGameSound setPlaySound(boolean playSound) {
        isPlaySound = playSound;
        return mGameSoundBg;
    }

    public BaseGameSound setDivSsoundBet(int divSsoundBet) {
        this.divSsoundBet = divSsoundBet;
        return mGameSoundBg;
    }

    public BaseGameSound setLoopNumber(int loopNumber) {
        this.loopNumber = loopNumber;
        return mGameSoundBg;
    }
}
