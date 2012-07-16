package jp.tohhy.ejmp.utils;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;
import jp.tohhy.ejmp.sounds.midi.MIDIPlayer;
import jp.tohhy.ejmp.sounds.midi.MIDISound;
import jp.tohhy.ejmp.sounds.mp3.Mp3Player;
import jp.tohhy.ejmp.sounds.mp3.Mp3Sound;
import jp.tohhy.ejmp.sounds.wave.WavePlayer;
import jp.tohhy.ejmp.sounds.wave.WaveSound;

public class PlayerUtils {

    /**
     * メディアから適切なプレイヤーを生成して返す.
     * @param ext ext 拡張子（.を含まない）
     * @param arg メディアに与える引数
     * @return 適切なメディア
     */
    public static MediaPlayer createSuitablePlayer(Media media) {
        if(media != null) {
            try {
                return getSuitablePlayerClass(media).newInstance();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * メディアから判断して適切なプレイヤークラスを返す.
     * @param ext 拡張子（.を含まない）
     * @return 拡張子に対応したメディアクラス
     */
    public static Class<? extends MediaPlayer> getSuitablePlayerClass(Media media) {
        if(media instanceof MIDISound) {
            return MIDIPlayer.class;
        } else if(media instanceof Mp3Sound) {
            return Mp3Player.class;
        } else if(media instanceof WaveSound) {
            return WavePlayer.class;
        }
        return null;
    }

}
