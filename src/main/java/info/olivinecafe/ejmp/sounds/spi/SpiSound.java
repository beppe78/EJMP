package info.olivinecafe.ejmp.sounds.spi;

import info.olivinecafe.ejmp.interfaces.AbstractMedia;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public abstract class SpiSound extends AbstractMedia {
    protected AudioFileFormat fileFormat;
    protected AudioFormat format;
    protected AudioInputStream stream;
    
    {
        init();
    }

    public SpiSound(File file) {
        super(file);
    }

    public SpiSound(String resourcePath) {
        super(resourcePath);
    }

    public SpiSound(URI uri) {
        super(uri);
    }

    public SpiSound(URL url) {
        super(url);
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
                this.fileFormat = getFormatFromURL(getURL());
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return fileFormat;
    }
    
    public AudioFormat getFormat() {
        if(format == null) {
            AudioFileFormat aff = getFileFormat();
            if(aff != null) return aff.getFormat();
        }
        return format;
    }
    
    protected void init() {
        getDecodedStream();
        getFileFormat();
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
    
    protected AudioFileFormat getFormatFromURL(URL url) 
            throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioFileFormat(url);
    }

    public AudioInputStream getDecodedStream() {
        return getRawStream();
    }

}
