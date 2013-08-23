package info.olivinecafe.ejmp.sounds.wave;

import info.olivinecafe.ejmp.sounds.spi.SpiSound;

import java.io.File;
import java.net.URI;
import java.net.URL;


public class WaveSound extends SpiSound {

    public WaveSound(File file) {
        super(file);
    }

    public WaveSound(String resourcePath) {
        super(resourcePath);
    }

    public WaveSound(URI uri) {
        super(uri);
    }

    public WaveSound(URL url) {
        super(url);
    }

    public MediaType getMediaType() {
        return MediaType.WAVE;
    }
}
