package info.olivinecafe.ejmp.sounds.mp3;

import info.olivinecafe.ejmp.interfaces.Media;
import info.olivinecafe.ejmp.sounds.spi.SpiPlayer;
import info.olivinecafe.ejmp.sounds.spi.SpiSound;

public class Mp3Player extends SpiPlayer {
    private Mp3Sound media;

    public void setMedia(Media media) {
        if(getMedia() != media)
        if(media instanceof Mp3Sound) {
            this.media = (Mp3Sound)media;
            rewind();
        }
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }
}
