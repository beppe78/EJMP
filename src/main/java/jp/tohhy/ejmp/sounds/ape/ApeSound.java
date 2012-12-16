package jp.tohhy.ejmp.sounds.ape;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class ApeSound extends SpiSound {
    private AudioInputStream decodedStream;

    public ApeSound(File file) {
        super(file);
    }

    public ApeSound(String resourcePath) {
        super(resourcePath);
    }

    public ApeSound(URI uri) {
        super(uri);
    }

    public ApeSound(URL url) {
        super(url);
    }
    
    public MediaType getMediaType() {
        return MediaType.APE;
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
    }
    
    public AudioInputStream getDecodedStream() {
        if(decodedStream == null)
            decodedStream = AudioSystem.getAudioInputStream(getFormat(), getRawStream());
        return decodedStream;
    }
}
