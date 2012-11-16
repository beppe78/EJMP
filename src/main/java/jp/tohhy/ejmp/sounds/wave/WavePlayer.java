package jp.tohhy.ejmp.sounds.wave;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class WavePlayer extends SpiPlayer {
    private WaveSound media;

    public void setMedia(Media media) {
        if(media instanceof WaveSound)
            this.media = (WaveSound)media;
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }
}