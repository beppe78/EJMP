package info.olivinecafe.ejmp.sounds.aiff;

import info.olivinecafe.ejmp.interfaces.Media;
import info.olivinecafe.ejmp.sounds.spi.SpiPlayer;
import info.olivinecafe.ejmp.sounds.spi.SpiSound;

public class AiffPlayer extends SpiPlayer {
    private AiffSound media;

    public void setMedia(Media media) {
        if(getMedia() != media)
        if(media instanceof AiffSound) {
            this.media = (AiffSound) media;
            rewind();
        }
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }

}
