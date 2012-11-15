package jp.tohhy.ejmp.sounds.wave;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import jp.tohhy.ejmp.interfaces.AbstractMedia;
import jp.tohhy.ejmp.utils.StreamUtils;

public class WaveSound extends AbstractMedia {
    private AudioInputStream audio;

    public WaveSound(String resourcePath) {
        super(resourcePath);
        try {
            loadAudio(getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public WaveSound(File file) {
        super(file);
        try {
            loadAudio(getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public MediaType getMediaType() {
        return MediaType.WAVE;
    }

    public AudioInputStream getAudio() {
        return audio;
    }


    public void dispose() throws Exception {
        audio.close();
    }

    
    private void loadAudio(URL url) throws UnsupportedAudioFileException, IOException {
        this.audio = AudioSystem.getAudioInputStream(
                StreamUtils.getURLAsStream(url));
    }


    public void reload() {
        try {
            if(audio != null)
                audio.close();
            loadAudio(getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
