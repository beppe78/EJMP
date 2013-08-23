package info.olivinecafe.ejmp.sounds.spi;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


public class AiffSound extends SpiSound {
    private AudioInputStream decodedStream;

    public AiffSound(File file) {
        super(file);
    }

    public AiffSound(String resourcePath) {
        super(resourcePath);
    }

    public AiffSound(URI uri) {
        super(uri);
    }

    public AiffSound(URL url) {
        super(url);
    }
    
    public MediaType getMediaType() {
        return MediaType.AIFF;
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
