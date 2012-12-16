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
    private AudioFormat format;
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
    public void dispose() {
        super.dispose();
        if(decodedStream != null) {
            try {
                decodedStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        format = null;
        decodedStream = null;
    }
    
    public AudioFormat getFormat() {
        if(this.format == null) {
            final AudioFormat baseFormat = getFileFormat().getFormat();
            int nSampleSizeInBits = baseFormat.getSampleSizeInBits();
            if (nSampleSizeInBits <= 0) nSampleSizeInBits = 16;
            if ((baseFormat.getEncoding() == AudioFormat.Encoding.ULAW) || 
                    (baseFormat.getEncoding() == AudioFormat.Encoding.ALAW)) nSampleSizeInBits = 16;
            if (nSampleSizeInBits != 8) nSampleSizeInBits = 16;
            this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                    baseFormat.getSampleRate(), nSampleSizeInBits, 
                    baseFormat.getChannels(), 
                    baseFormat.getChannels() * (nSampleSizeInBits / 8), 
                    baseFormat.getSampleRate(), 
                    false);
        }
        return this.format;
    }
    
    public AudioInputStream getDecodedStream() {
        if(decodedStream == null)
            decodedStream = AudioSystem.getAudioInputStream(getFormat(), getRawStream());
        return decodedStream;
    }
}
