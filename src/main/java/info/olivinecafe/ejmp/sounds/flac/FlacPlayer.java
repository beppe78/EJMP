package info.olivinecafe.ejmp.sounds.flac;

import info.olivinecafe.ejmp.interfaces.Media;
import info.olivinecafe.ejmp.sounds.spi.SpiPlayer;
import info.olivinecafe.ejmp.sounds.spi.SpiSound;

public class FlacPlayer extends SpiPlayer {
    private FlacSound media;

    public void setMedia(Media media) {
        if(getMedia() != media)
        if(media instanceof FlacSound) {
            this.media = (FlacSound) media;
            rewind();
        }
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }

}
