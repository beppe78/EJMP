package jp.tohhy.ejmp.sounds.mp3;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

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
