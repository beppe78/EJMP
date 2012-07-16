package jp.tohhy.ejmp.sounds.wave;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.utils.StreamUtils;

public class WaveSound extends Media {
    private AudioInputStream audio;

    public WaveSound(String resourcePath) {
        super(resourcePath);
        try {
            this.audio = AudioSystem.getAudioInputStream(
                    StreamUtils.getResourceAsStream(resourcePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public WaveSound(File file) {
        super(file);
        try {
            this.audio = AudioSystem.getAudioInputStream(
                    StreamUtils.getFileAsStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return getFile().getName();
    }

    public MediaType getMediaType() {
        return MediaType.WAVE;
    }

    public AudioInputStream getAudio() {
        return audio;
    }

    @Override
    public void dispose() throws Exception {
        audio.close();
    }
}
