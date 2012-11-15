package jp.tohhy.ejmp.sounds.ogg;

import java.io.File;

import jp.tohhy.ejmp.interfaces.AbstractMedia;

public class OggSound extends AbstractMedia {

    public OggSound(File file) {
        super(file);
    }
    
    public OggSound(String resourcePath) {
        super(resourcePath);
    }


    public void reload() {
        
    }


    public MediaType getMediaType() {
        return null;
    }


    public void dispose() throws Exception {
        
    }

}
