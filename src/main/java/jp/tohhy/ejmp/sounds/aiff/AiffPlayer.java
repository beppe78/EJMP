package jp.tohhy.ejmp.sounds.aiff;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class AiffPlayer extends SpiPlayer {
    private AiffSound media;

    public void setMedia(Media media) {
        if(media instanceof AiffSound)
            this.media = (AiffSound) media;
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }

}
