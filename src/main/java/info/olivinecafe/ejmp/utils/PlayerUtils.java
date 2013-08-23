package info.olivinecafe.ejmp.utils;

import info.olivinecafe.ejmp.interfaces.Media;
import info.olivinecafe.ejmp.interfaces.MediaPlayer;
import info.olivinecafe.ejmp.sounds.aiff.AiffPlayer;
import info.olivinecafe.ejmp.sounds.aiff.AiffSound;
import info.olivinecafe.ejmp.sounds.ape.ApePlayer;
import info.olivinecafe.ejmp.sounds.ape.ApeSound;
import info.olivinecafe.ejmp.sounds.au.AUPlayer;
import info.olivinecafe.ejmp.sounds.au.AUSound;
import info.olivinecafe.ejmp.sounds.flac.FlacPlayer;
import info.olivinecafe.ejmp.sounds.flac.FlacSound;
import info.olivinecafe.ejmp.sounds.midi.MIDIPlayer;
import info.olivinecafe.ejmp.sounds.midi.MIDISound;
import info.olivinecafe.ejmp.sounds.mp3.Mp3Player;
import info.olivinecafe.ejmp.sounds.mp3.Mp3Sound;
import info.olivinecafe.ejmp.sounds.ogg.OggPlayer;
import info.olivinecafe.ejmp.sounds.ogg.OggSound;
import info.olivinecafe.ejmp.sounds.wave.WavePlayer;
import info.olivinecafe.ejmp.sounds.wave.WaveSound;

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
        System.err.println("can't create player for:" + media);
        return null;
    }

    /**
     * メディアから判断して適切なプレイヤークラスを返す.
     * @param ext 拡張子（.を含まない）
     * @return 拡張子に対応したメディアプレイヤークラス
     */
    public static Class<? extends MediaPlayer> getSuitablePlayerClass(Media media) {
        if(media instanceof MIDISound) {
            return MIDIPlayer.class;
        } else if(media instanceof Mp3Sound) {
            return Mp3Player.class;
        } else if(media instanceof WaveSound) {
            return WavePlayer.class;
//        } else if(media instanceof AACSound) {
//            return AACPlayer.class;
        } else if(media instanceof AUSound) {
            return AUPlayer.class;
        } else if(media instanceof OggSound) {
            return OggPlayer.class;
        } else if(media instanceof AiffSound) {
            return AiffPlayer.class;
        } else if(media instanceof FlacSound) {
            return FlacPlayer.class;
        } else if(media instanceof ApeSound) {
            return ApePlayer.class;
        }
        return null;
    }

}
