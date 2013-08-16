package jp.tohhy.ejmp.sounds.au;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import jp.tohhy.ejmp.sounds.spi.SpiSound;

public class AUSound extends SpiSound {
    private AudioFormat format;
    private AudioInputStream decodedStream;

    public AUSound(File file) {
        super(file);
    }

    public AUSound(String resourcePath) {
        super(resourcePath);
    }

    public AUSound(URI uri) {
        super(uri);
    }

    public AUSound(URL url) {
        super(url);
    }
    
    public MediaType getMediaType() {
        return MediaType.AU;
    }
    
    @Override
    public AudioFormat getFormat() {
        if(this.format == null) {
            final AudioFormat baseFormat = getFileFormat().getFormat();
            this.format = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    baseFormat.getSampleSizeInBits() * 2,
                    baseFormat.getChannels(),
                    baseFormat.getFrameSize() * 2,
                    baseFormat.getFrameRate(),
                    true);
        }
        return this.format;
    }
    
    @Override
    public void dispose() throws IOException {
        super.dispose();
        if(decodedStream != null) {
            decodedStream.close();
        }
        decodedStream = null;
        format = null;
    }
    
    public AudioInputStream getDecodedStream() {
        if(decodedStream == null)
            decodedStream = AudioSystem.getAudioInputStream(getFormat(), getRawStream());
        return decodedStream;
    }
}
