package info.olivinecafe.ejmp.utils;

import info.olivinecafe.ejmp.interfaces.AbstractMedia;
import info.olivinecafe.ejmp.sounds.midi.MIDISound;
import info.olivinecafe.ejmp.sounds.spi.AUSound;
import info.olivinecafe.ejmp.sounds.spi.AiffSound;
import info.olivinecafe.ejmp.sounds.spi.ApeSound;
import info.olivinecafe.ejmp.sounds.spi.FlacSound;
import info.olivinecafe.ejmp.sounds.spi.Mp3Sound;
import info.olivinecafe.ejmp.sounds.spi.OggSound;
import info.olivinecafe.ejmp.sounds.spi.WaveSound;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MediaUtils {
    
    /**
     * メディアURLの拡張子から判断して適切なメディアを返す.
     * @param location メディアの位置
     * @return 拡張子に対応したメディア
     */
    public static AbstractMedia createSuitableMedia(MediaLocation location) {
        return createSuitableMedia(location.getExtension(), location);
    }

    /**
     * 拡張子から判断して適切なメディアクラスを返す.
     * @param ext 拡張子（.を含まない）
     * @return 拡張子に対応したメディアクラス
     */
    private static Class<? extends AbstractMedia> getSuitableMediaClassForExtension(String extension) {
        final String ext = extension.toLowerCase();
        if(ext.equals("aif") || ext.equals("aiff") || ext.equals("aifc")) {
            return AiffSound.class;
        } else if(ext.equals("wav")) {
            return WaveSound.class;
        } else if(ext.equals("mp3")) {
            return Mp3Sound.class;
        } else if(ext.equals("wma")) {
            //未対応
        } else if(ext.equals("flac")) {
            return FlacSound.class;
        } else if(ext.equals("au")) {
            return AUSound.class;
        } else if(ext.equals("ogg") || ext.equals("ogx") || ext.equals("oga")) {
            return OggSound.class;
        } else if(ext.equals("aac") || ext.equals("mp4") || ext.equals("m4a") || ext.equals("m4r")) {
//            return AACSound.class;
            //未対応
        } else if(ext.equals("mid") || ext.equals("midi")) {
            return MIDISound.class;
        } else if(ext.equals("ape")) {
            return ApeSound.class;
        }
        return null;
    }

    /**
     * 拡張子とメディアに与える引数から適切なメディアを生成して返す.
     * @param ext ext 拡張子（.を含まない）
     * @param arg メディアのコンストラクタに与える引数
     * @return 適切なメディア
     */
    private static AbstractMedia createSuitableMedia(String ext, Object arg) {
        final Class<? extends AbstractMedia> suitableMedia = getSuitableMediaClassForExtension(ext);
        if(suitableMedia != null) {
            try {
                Object[] args = {arg};
                Constructor<? extends AbstractMedia> constructor = 
                        suitableMedia.getConstructor(arg.getClass());
                return constructor.newInstance(args);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
