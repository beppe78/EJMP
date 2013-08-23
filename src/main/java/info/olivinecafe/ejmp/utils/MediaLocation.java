package info.olivinecafe.ejmp.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MediaLocation {
    private final URL url;
    
    public MediaLocation(URI uri) {
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.url = url;
    }
    
    public MediaLocation(URL url) {
        this.url = url;
    }
    
    public MediaLocation(String resourcePath) {
        this.url = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        if(getUrl() == null) throw new IllegalArgumentException("resource not found: " + resourcePath);
    }
    
    public MediaLocation(File file) {
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
    
    @Override
    public String toString() {
        return url.toString();
    }

}
