package jp.tohhy.ejmp.interfaces;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public abstract class AbstractMedia implements Media {
    private final URL url;
    
    /**
     * URIを指定してメディアを読み込む.
     * @param uri
     */
    public AbstractMedia (URI uri) {
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.url = url;
    }
    
    /**
     * URLを指定してメディアを読み込む.
     * @param url
     */
    public AbstractMedia(URL url) {
        this.url = url;
    }

    /**
     * 内部リソースのパスを指定してメディアを読み込む.
     * @param resourcePath
     */
    public AbstractMedia(String resourcePath) {
        this.url = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
    }

    /**
     * 外部ファイルを指定してメディアを読み込む.
     * @param file
     */
    public AbstractMedia(File file) {
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public String getName() {
        if(url != null)
            return url.getFile();
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
