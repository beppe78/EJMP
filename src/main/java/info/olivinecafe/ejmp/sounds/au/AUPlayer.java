package info.olivinecafe.ejmp.sounds.au;

import info.olivinecafe.ejmp.interfaces.Media;
import info.olivinecafe.ejmp.sounds.spi.SpiPlayer;
import info.olivinecafe.ejmp.sounds.spi.SpiSound;

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
