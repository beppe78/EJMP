package info.olivinecafe.ejmp.sounds.spi;


import info.olivinecafe.ejmp.media.MediaLocation;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


public class AiffSound extends SpiSound {
    private AudioInputStream decodedStream;

    public AiffSound(MediaLocation location) {
        super(location);
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
