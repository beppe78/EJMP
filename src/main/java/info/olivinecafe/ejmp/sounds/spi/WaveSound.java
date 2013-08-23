package info.olivinecafe.ejmp.sounds.spi;


import info.olivinecafe.ejmp.utils.MediaLocation;


public class WaveSound extends SpiSound {

    public WaveSound(MediaLocation location) {
        super(location);
    }

    public MediaType getMediaType() {
        return MediaType.WAVE;
    }
}
