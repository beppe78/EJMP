package jp.tohhy.ejmp.sounds.ogg;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class OggSound extends SpiSound {
    private AudioInputStream decodedStream;

    public OggSound(File file) {
        super(file);
    }
    
    public OggSound(String resourcePath) {
        super(resourcePath);
    }
    
    public OggSound(URL url) {
        super(url);
    }
    
    public OggSound(URI uri) {
        super(uri);
    }

    public MediaType getMediaType() {
        return MediaType.OGG;
    }
    
    @Override
    public void init() {
        super.init();
        getDecodedStream();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if(decodedStream != null) {
            try {
                decodedStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        decodedStream = null;
    }
    
    public AudioFormat getFormat() {
        final AudioFormat baseFormat = getFileFormat().getFormat();
        return new AudioFormat(
                baseFormat.getSampleRate(),
                baseFormat.getSampleSizeInBits(),
                baseFormat.getChannels(),
                true,
                false);
    }
    
    public AudioInputStream getDecodedStream() {
        if(decodedStream == null)
            decodedStream = AudioSystem.getAudioInputStream(getFormat(), getStream());
        return decodedStream;
    }
}
