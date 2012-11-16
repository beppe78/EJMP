package jp.tohhy.ejmp.sounds.mp3;

import java.io.IOException;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class Mp3Player extends SpiPlayer {
    private Mp3Sound media;

    public void setMedia(Media media) {
        if(media instanceof Mp3Sound)
            this.media = (Mp3Sound)media;
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
    }
    
    @Override
    protected int readStream(SpiSound media, byte[] buffer) throws IOException {
        return ((Mp3Sound)media).getDecodedStream().read(buffer, 0, buffer.length);
    }
}
