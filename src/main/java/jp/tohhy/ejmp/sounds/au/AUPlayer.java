package jp.tohhy.ejmp.sounds.au;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class AUPlayer extends SpiPlayer {
    private AUSound media;

    public void setMedia(Media media) {
        if(getMedia() != media)
        if(media instanceof AUSound) {
            this.media = (AUSound) media;
            rewind();
        }
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }

}
