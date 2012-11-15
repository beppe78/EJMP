package jp.tohhy.ejmp.sounds.wave;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.media.MediaLocator;

import jp.tohhy.ejmp.interfaces.AbstractMedia;

import com.sun.media.protocol.DataSource;

public class WaveSound extends AbstractMedia {
    private DataSource dataSource;

    public WaveSound(String resourcePath) {
        super(resourcePath);
        init();
    }

    public WaveSound(File file) {
        super(file);
        init();
    }
    
    public WaveSound(URL url) {
        super(url);
        init();
    }
    
    public WaveSound(URI uri) {
        super(uri);
        init();
    }
    
    private final void init() {
        dataSource = new DataSource();
        getDataSource().setLocator(new MediaLocator(getUrl()));
        try {
            dataSource.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public MediaType getMediaType() {
        return MediaType.WAVE;
    }


    public void dispose() throws Exception {
        dataSource.disconnect();
    }

    public void reload() {
        final MediaLocator locator = dataSource.getLocator();
        dataSource.disconnect();
        dataSource = new DataSource();
        dataSource.setLocator(locator);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
