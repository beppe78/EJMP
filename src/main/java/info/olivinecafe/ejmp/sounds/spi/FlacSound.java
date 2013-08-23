package info.olivinecafe.ejmp.sounds.spi;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.kc7bfi.jflac.FLACDecoder;
import org.kc7bfi.jflac.metadata.StreamInfo;
import org.kc7bfi.jflac.sound.spi.Flac2PcmAudioInputStream;
import org.kc7bfi.jflac.sound.spi.FlacFileFormatType;

public class FlacSound extends SpiSound {
    private AudioInputStream decodedStream;
    private FLACDecoder decoder;

    public FlacSound(File file) {
        super(file);
    }

    public FlacSound(String resourcePath) {
        super(resourcePath);
    }

    public FlacSound(URI uri) {
        super(uri);
    }

    public FlacSound(URL url) {
        super(url);
    }
    
    @Override
    protected void init() {
        try {
            decoder = new FLACDecoder(getURL().openStream());
            decoder.decode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.init();
    }
    
    public MediaType getMediaType() {
        return MediaType.FLAC;
    }
    
    @Override
    public void dispose() throws IOException {
        super.dispose();
        if(decodedStream != null) {
            decodedStream.close();
        }
        decodedStream = null;
    }
    
    @Override
    protected AudioFileFormat getFormatFromURL(URL url)
            throws UnsupportedAudioFileException, IOException {
        StreamInfo info = decoder.getStreamInfo();
        AudioFormat format = info.getAudioFormat();
        return new AudioFileFormat(FlacFileFormatType.FLAC, format, 
                (int)info.getTotalSamples());
    }
    
    protected AudioInputStream getRawStream() {
        if(stream == null)
            try {
                this.stream = new Flac2PcmAudioInputStream(
                        getURL().openStream(), 
                        getFormat(), decoder.getSamplesDecoded());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return stream;
    }
    
}
