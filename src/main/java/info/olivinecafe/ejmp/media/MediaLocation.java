package info.olivinecafe.ejmp.media;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * メディアが存在する位置をURLで保持する.
 * @author tohhy
 */
public class MediaLocation {
    private final URL url;
    
    /**
     * URLからMediaLocationを初期化する.
     * @param url メディアの位置を表すURL
     */
    public MediaLocation(URL url) {
        this.url = url;
    }
    
    /**
     * URIからMediaLocationを初期化する.
     * @param uri メディアの位置を表すURI
     */
    public MediaLocation(URI uri) {
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.url = url;
    }

    /**
     * リソースパス文字列からMediaLocationを初期化する.
     * @param resourcePath メディアリソースの位置を表すパス文字列
     */
    public MediaLocation(String resourcePath) {
        this.url = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        if(getUrl() == null) 
            throw new IllegalArgumentException("resource not found: " + resourcePath);
    }
    
    /**
     * ファイルオブジェクトからMediaLocationを初期化する.
     * @param file メディアの位置を表すFileオブジェクト
     */
    public MediaLocation(File file) {
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.url = url;
    }

    /**
     * @return getUrl().toString()
     */
    @Override
    public String toString() {
        return url.toString();
    }

    /**
     * メディアの位置を表すURLを取得する.
     * @return メディアの位置を表すURL
     */
    public URL getUrl() {
        return url;
    }
    
    /**
     * メディアのファイル名を取得する.getURL().getFile()と同じ.
     * @return メディアのファイル名
     */
    public String getName() {
        return url.getFile();
    }
    
    /**
     * メディアの拡張子を取得する.
     * @return メディアの拡張子
     */
    public String getExtension() {
        return getExtension(getName());
    }
    
    private static String getExtension(String fileName) {
        if (fileName != null) {
            int dotindex = fileName.lastIndexOf(".");
            if (dotindex == -1) {
                return fileName;
            } else {
                return fileName.substring(dotindex + 1);
            }
        }
        return null;
    }
}
