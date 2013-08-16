package jp.tohhy.ejmp.sounds.spi;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import jp.tohhy.ejmp.interfaces.AbstractMedia;

public abstract class SpiSound extends AbstractMedia {
    protected AudioFileFormat fileFormat;
    protected AudioFormat format;
    protected AudioInputStream stream;

    public SpiSound(File file) {
        super(file);
        init();
    }

    public SpiSound(String resourcePath) {
        super(resourcePath);
        init();
    }

    public SpiSound(URI uri) {
        super(uri);
        init();
    }

    public SpiSound(URL url) {
        super(url);
        init();
    }
    
    private void init() {
        getDecodedStream();
        getFileFormat();
    }

    public void reload() {
        try {
            dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    public void dispose() throws IOException {
        if(stream != null) {
            stream.close();
        }
        stream = null;
        fileFormat = null;
    }
    
    public AudioFileFormat getFileFormat() {
        if(fileFormat == null)
            try {
                this.fileFormat = AudioSystem.getAudioFileFormat(getURL());
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return fileFormat;
    }
    
    public AudioFormat getFormat() {
        if(format == null)
            return getFileFormat().getFormat();
        return format;
    }
    
    protected AudioInputStream getRawStream() {
        if(stream == null)
            try {
                this.stream = AudioSystem.getAudioInputStream(getURL());
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return stream;
    }
    
    public AudioInputStream getDecodedStream() {
        return getRawStream();
    }

}
