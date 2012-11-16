package jp.tohhy.ejmp.sounds.ogg;

import java.io.File;
import java.net.URI;
import java.net.URL;

import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class OggSound extends SpiSound {

    public OggSound(File file) {
        super(file);
    }
    
    public OggSound(String resourcePath) {
        super(resourcePath);
    }
    
    public OggSound(URL url) {
        super(url);
    }
    
    public OggSound(URI uri) {
        super(uri);
    }

    public MediaType getMediaType() {
        return MediaType.OGG;
    }

}
