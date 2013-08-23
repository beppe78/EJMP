package info.olivinecafe.ejmp.sounds.wave;

import info.olivinecafe.ejmp.interfaces.Media;
import info.olivinecafe.ejmp.sounds.spi.SpiPlayer;
import info.olivinecafe.ejmp.sounds.spi.SpiSound;

public class WavePlayer extends SpiPlayer {
    private WaveSound media;

    public void setMedia(Media media) {
        if(media instanceof WaveSound) {
            this.media = (WaveSound)media;
            rewind();
        }
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }
}