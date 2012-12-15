package jp.tohhy.ejmp.sounds.ape;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class ApePlayer extends SpiPlayer {
    private ApeSound media;

    public void setMedia(Media media) {
        if(media instanceof ApeSound)
            this.media = (ApeSound) media;
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }

}
