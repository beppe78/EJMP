package info.olivinecafe.ejmp.utils;

import info.olivinecafe.ejmp.interfaces.Media;
import info.olivinecafe.ejmp.interfaces.MediaPlayer;
import info.olivinecafe.ejmp.sounds.midi.MIDIPlayer;
import info.olivinecafe.ejmp.sounds.midi.MIDISound;
import info.olivinecafe.ejmp.sounds.spi.AUSound;
import info.olivinecafe.ejmp.sounds.spi.AiffSound;
import info.olivinecafe.ejmp.sounds.spi.ApeSound;
import info.olivinecafe.ejmp.sounds.spi.FlacSound;
import info.olivinecafe.ejmp.sounds.spi.Mp3Sound;
import info.olivinecafe.ejmp.sounds.spi.OggSound;
import info.olivinecafe.ejmp.sounds.spi.SpiPlayer;
import info.olivinecafe.ejmp.sounds.spi.WaveSound;

public class PlayerUtils {

    /**
     * メディアから適切なプレイヤーを生成して返す.
     * @param ext ext 拡張子（.を含まない）
     * @param arg メディアに与える引数
     * @return 適切なメディア
     */
    @SuppressWarnings("rawtypes")
    public static MediaPlayer createSuitablePlayer(Media media) {
        if(media != null) {
            try {
                return getSuitablePlayerInstance(media);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        System.err.println("can't create player for:" + media);
        return null;
    }

    /**
     * メディアから適切なプレイヤーを生成して返す.
     * @param ext 拡張子（.を含まない）
     * @return 拡張子に対応したメディアプレイヤークラス
     */
    @SuppressWarnings("rawtypes")
    private static MediaPlayer getSuitablePlayerInstance(Media media) {
        if(media instanceof MIDISound) {
            return new MIDIPlayer();
        } else if(media instanceof Mp3Sound) {
            return new SpiPlayer<Mp3Sound>();
        } else if(media instanceof WaveSound) {
            return new SpiPlayer<WaveSound>();
//        } else if(media instanceof AACSound) {
//            return new SpiPlayer<AACSound>();
        } else if(media instanceof AUSound) {
            return new SpiPlayer<AUSound>();
        } else if(media instanceof OggSound) {
            return new SpiPlayer<OggSound>();
        } else if(media instanceof AiffSound) {
            return new SpiPlayer<AiffSound>();
        } else if(media instanceof FlacSound) {
            return new SpiPlayer<FlacSound>();
        } else if(media instanceof ApeSound) {
            return new SpiPlayer<ApeSound>();
        }
        return null;
    }

}
