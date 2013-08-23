package info.olivinecafe.ejmp.interfaces;

import info.olivinecafe.ejmp.utils.MediaLocation;

import java.net.URL;

public abstract class AbstractMedia implements Media {
    private final MediaLocation location;
    
    /**
     * メディアの位置を指定してメディアを読み込む.
     * @param uri
     */
    public AbstractMedia (MediaLocation location) {
        this.location = location;
    }
    
    public MediaLocation getLocation() {
        return location;
    }
    
    public URL getURL() {
        return location.getUrl();
    }

    public String getName() {
        if(location.getUrl() != null)
            return location.getUrl().getFile();
        return "file not found";
    }

    /**
     * getName()を呼び出す.
     */
    @Override
    public String toString() {
        return getName();
    }
}
