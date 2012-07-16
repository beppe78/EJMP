package jp.tohhy.ejmp.sounds.wave;

import java.io.File;
import java.io.FileNotFoundException;
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
            loadAudio(resourcePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public WaveSound(File file) {
        super(file);
        try {
            loadAudio(file);
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

    private void loadAudio(String resourcePath)
    throws UnsupportedAudioFileException, IOException {
        this.audio = AudioSystem.getAudioInputStream(
                StreamUtils.getResourceAsStream(resourcePath));
    }

    private void loadAudio(File file)
    throws FileNotFoundException, UnsupportedAudioFileException, IOException {
        this.audio = AudioSystem.getAudioInputStream(
                StreamUtils.getFileAsStream(file));
    }

    @Override
    public void reload() {
        try {
            if(audio != null)
                audio.close();
            if(getFile() != null && getFile().exists()) {
                loadAudio(getFile());
            } else if(getResourcePath() != null) {
                loadAudio(getResourcePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
