package jp.tohhy.ejmp.sounds.ogg;

import java.io.File;

import jp.tohhy.ejmp.interfaces.Media;

public class OggSound extends Media {

    public OggSound(File file) {
        super(file);
    }
    
    public OggSound(String resourcePath) {
        super(resourcePath);
    }

    @Override
    public void reload() {
        
    }

    @Override
    public MediaType getMediaType() {
        return null;
    }

    @Override
    public void dispose() throws Exception {
        
    }

}
