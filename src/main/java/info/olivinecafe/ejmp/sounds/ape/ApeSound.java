package info.olivinecafe.ejmp.sounds.ape;

import info.olivinecafe.ejmp.sounds.spi.SpiSound;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


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
    public void dispose() throws IOException {
        super.dispose();
        if(decodedStream != null) {
            decodedStream.close();
        }
        decodedStream = null;
    }
    
    public AudioInputStream getDecodedStream() {
        if(decodedStream == null)
            decodedStream = AudioSystem.getAudioInputStream(getFormat(), getRawStream());
        return decodedStream;
    }
}
