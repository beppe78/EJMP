package jp.tohhy.ejmp.sounds.ogg;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class OggPlayer extends SpiPlayer {
    private OggSound media;
    

    public void setMedia(Media media) {
        if(getMedia() != media)
        if(media instanceof OggSound) {
            this.media = (OggSound)media;
            rewind();
        }
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }
}
