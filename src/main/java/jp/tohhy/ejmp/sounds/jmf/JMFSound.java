package jp.tohhy.ejmp.sounds.jmf;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.media.MediaLocator;

import jp.tohhy.ejmp.interfaces.AbstractMedia;

import com.sun.media.protocol.DataSource;

public abstract class JMFSound extends AbstractMedia {
    private DataSource dataSource;

    public JMFSound(String resourcePath) {
        super(resourcePath);
        init();
    }

    public JMFSound(File file) {
        super(file);
        init();
    }
    
    public JMFSound(URL url) {
        super(url);
        init();
    }
    
    public JMFSound(URI uri) {
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
