package jp.tohhy.ejmp.sounds.spi;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import jp.tohhy.ejmp.interfaces.AbstractMedia;

public abstract class SpiSound extends AbstractMedia {
    protected AudioFileFormat fileFormat;
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
    
    public void init() {
        getStream();
        getFileFormat();
    }

    public void reload() {
        dispose();
        init();
    }

    public void dispose() {
        if(stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stream = null;
        fileFormat = null;
    }
    
    public AudioFileFormat getFileFormat() {
        if(fileFormat == null)
            try {
                this.fileFormat = AudioSystem.getAudioFileFormat(getUrl());
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return fileFormat;
    }
    
    public AudioInputStream getStream() {
        if(stream == null)
            try {
                this.stream = AudioSystem.getAudioInputStream(getUrl());
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return stream;
    }

}