package info.olivinecafe.ejmp.media;


import java.net.URL;

/**
 * 任意のメディア（サウンド、ムービー等）を表す基底クラス.
 * @author tohhy
 */
public abstract class AbstractMedia implements Media {
    private final MediaLocation location;
    
    /**
     * メディアの位置を指定してメディアを読み込む.
     * @param uri
     */
    public AbstractMedia (MediaLocation location) {
        this.location = location;
    }
    
    /**
     * このメディアが存在する位置を取得する.
     * @return このメディアが存在する位置
     */
    public MediaLocation getLocation() {
        return location;
    }
    
    /**
     * このメディアが存在するURLを取得する.
     * @return このメディアが存在するURL
     */
    public URL getURL() {
        return location.getUrl();
    }

    /**
     * このメディアの名前を取得する.
     */
    public String getName() {
        if(location.getUrl() != null)
            return location.getName();
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
