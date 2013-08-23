package info.olivinecafe.ejmp.sounds.spi;


import info.olivinecafe.ejmp.utils.MediaLocation;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


public class AUSound extends SpiSound {
    private AudioFormat format;
    private AudioInputStream decodedStream;

    public AUSound(MediaLocation location) {
        super(location);
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
