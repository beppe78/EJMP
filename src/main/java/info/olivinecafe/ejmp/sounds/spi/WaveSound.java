package info.olivinecafe.ejmp.sounds.spi;


import javax.sound.sampled.AudioInputStream;

import info.olivinecafe.ejmp.media.MediaLocation;


public class WaveSound extends SpiSound {

    public WaveSound(MediaLocation location) {
        super(location);
    }

    public MediaType getMediaType() {
        return MediaType.WAVE;
    }
    
    @Override
    public AudioInputStream getDecodedStream() {
        return super.getDecodedStream();
    }
}
