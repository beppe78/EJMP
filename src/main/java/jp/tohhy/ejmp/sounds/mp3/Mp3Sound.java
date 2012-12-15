package jp.tohhy.ejmp.sounds.mp3;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import jp.tohhy.ejmp.sounds.spi.SpiSound;


public class Mp3Sound extends SpiSound {
    private AudioFormat format;
    private AudioInputStream decodedStream;

    public Mp3Sound(File file) {
        super(file);
    }

    public Mp3Sound(String resourcePath) {
        super(resourcePath);
    }

    public Mp3Sound(URI uri) {
        super(uri);
    }

    public Mp3Sound(URL url) {
        super(url);
    }

    public MediaType getMediaType() {
        return MediaType.MP3;
    }

    
    @Override
    public void dispose() {
        super.dispose();
        if(decodedStream != null) {
            try {
                decodedStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        decodedStream = null;
        format = null;
    }
    
    
    public AudioFormat getFormat() {
        if(this.format == null) {
            final AudioFormat baseFormat = getFileFormat().getFormat();
            this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
        }
        return this.format;
    }
    
    public AudioInputStream getStream() {
        if(decodedStream == null) {
            decodedStream = AudioSystem.getAudioInputStream(getFormat(), getRawStream());
        }
        return decodedStream;
    }
}
