package info.olivinecafe.ejmp.sounds.ape;

import info.olivinecafe.ejmp.interfaces.Media;
import info.olivinecafe.ejmp.sounds.spi.SpiPlayer;
import info.olivinecafe.ejmp.sounds.spi.SpiSound;

public class ApePlayer extends SpiPlayer {
    private ApeSound media;

    public void setMedia(Media media) {
        if(getMedia() != media)
        if(media instanceof ApeSound) {
            this.media = (ApeSound) media;
            rewind();
        }
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }

}
