package jp.tohhy.ejmp.sounds.wave;

import java.io.File;
import java.net.URI;
import java.net.URL;

import jp.tohhy.ejmp.sounds.spi.SpiSound;

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
