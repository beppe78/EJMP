package jp.tohhy.ejmp.utils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import jp.tohhy.ejmp.interfaces.AbstractMedia;
import jp.tohhy.ejmp.sounds.aiff.AiffSound;
import jp.tohhy.ejmp.sounds.ape.ApeSound;
import jp.tohhy.ejmp.sounds.au.AUSound;
import jp.tohhy.ejmp.sounds.midi.MIDISound;
import jp.tohhy.ejmp.sounds.mp3.Mp3Sound;
import jp.tohhy.ejmp.sounds.ogg.OggSound;
import jp.tohhy.ejmp.sounds.wave.WaveSound;

public class MediaUtils {

    public static AbstractMedia createSuitableMedia(String resourcePath) {
        return createSuitableMedia(FileUtils.getExtension(resourcePath), resourcePath);
    }

    public static AbstractMedia createSuitableMedia(File file) {
        return createSuitableMedia(FileUtils.getExtension(file.getName()), file);
    }
    
    public static AbstractMedia createSuitableMedia(URL url) {
        return createSuitableMedia(FileUtils.getExtension(url.getFile()), url);
    }

    /**
     * 拡張子から判断して適切なメディアクラスを返す.
     * @param ext 拡張子（.を含まない）
     * @return 拡張子に対応したメディアクラス
     */
    private static Class<? extends AbstractMedia> getSuitableMediaClassForExtension(String extension) {
        final String ext = extension.toLowerCase();
        if(ext.equals("aif") || ext.equals("aiff")) {
            return AiffSound.class;
        } else if(ext.equals("wav")) {
            return WaveSound.class;
        } else if(ext.equals("mp3")) {
            return Mp3Sound.class;
        } else if(ext.equals("wma")) {
            //未対応
        } else if(ext.equals("au")) {
            return AUSound.class;
        } else if(ext.equals("ogg") || ext.equals("ogx") || ext.equals("oga")) {
            return OggSound.class;
        } else if(ext.equals("aac") || ext.equals("mp4") || ext.equals("m4a")) {
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
                final Object[] args = {arg};
                final Constructor<? extends AbstractMedia> constructor = suitableMedia.getConstructor(arg.getClass());
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
