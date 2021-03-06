package info.olivinecafe.ejmp.sounds.spi;


import info.olivinecafe.ejmp.media.MediaLocation;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;



public class Mp3Sound extends SpiSound {
    private AudioFormat format;
    private AudioInputStream decodedStream;

    public Mp3Sound(MediaLocation location) {
        super(location);
    }

    public MediaType getMediaType() {
        return MediaType.MP3;
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
    
    
    public AudioFormat getFormat() {
        if(this.format == null) {
            final AudioFormat baseFormat = getFileFormat().getFormat();
            this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
        }
        return this.format;
    }
    
    public AudioInputStream getDecodedStream() {
        if(decodedStream == null) {
            decodedStream = AudioSystem.getAudioInputStream(getFormat(), getRawStream());
        }
        return decodedStream;
    }
}
