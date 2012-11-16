package jp.tohhy.ejmp.sounds.ogg;

import java.io.IOException;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class OggPlayer extends SpiPlayer {
    private OggSound media;
    

    public void setMedia(Media media) {
        if(media instanceof OggSound)
            this.media = (OggSound)media;
    }
    
    @Override
    protected int readStream(SpiSound media, byte[] buffer) throws IOException {
        return ((OggSound)media).getDecodedStream().read(buffer, 0, buffer.length);
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }
}
