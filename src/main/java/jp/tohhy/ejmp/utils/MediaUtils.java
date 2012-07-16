package jp.tohhy.ejmp.utils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.au.AUSound;
import jp.tohhy.ejmp.sounds.midi.MIDISound;
import jp.tohhy.ejmp.sounds.mp3.Mp3Sound;
import jp.tohhy.ejmp.sounds.wave.WaveSound;

import org.apache.commons.io.FilenameUtils;

public class MediaUtils {

    public static Media createSuitableMedia(String resourcePath) {
        return createSuitableMedia(FilenameUtils.getExtension(resourcePath), resourcePath);
    }

    public static Media createSuitableMedia(File file) {
        return createSuitableMedia(FilenameUtils.getExtension(file.getName()), file);
    }

    /**
     * 拡張子から判断して適切なメディアクラスを返す.
     * @param ext 拡張子（.を含まない）
     * @return 拡張子に対応したメディアクラス
     */
    private static Class<? extends Media> getSuitableMediaClassForExtension(String extension) {
        final String ext = extension.toLowerCase();
        if(ext.equals("aif") || ext.equals("aiff")) {
            //未対応
        } else if(ext.equals("wav")) {
            return WaveSound.class;
        } else if(ext.equals("mp3")) {
            return Mp3Sound.class;
        } else if(ext.equals("wma")) {
            //未対応
        } else if(ext.equals("au") || ext.equals("au")) {
            return AUSound.class;
        } else if(ext.equals("ogg") || ext.equals("ogx") || ext.equals("oga")) {
            //未対応
        } else if(ext.equals("aac") || ext.equals("mp4") || ext.equals("m4a")) {
            //未対応
        } else if(ext.equals("mid") || ext.equals("midi")) {
            return MIDISound.class;
        }
        return null;
    }

    /**
     * 拡張子とメディアに与える引数から適切なメディアを生成して返す.
     * @param ext ext 拡張子（.を含まない）
     * @param arg メディアに与える引数
     * @return 適切なメディア
     */
    private static Media createSuitableMedia(String ext, Object arg) {
        final Class<? extends Media> suitableMedia = getSuitableMediaClassForExtension(ext);
        if(suitableMedia != null) {
            try {
                final Object[] args = {arg};
                final Constructor<? extends Media> constructor = suitableMedia.getConstructor(arg.getClass());
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
