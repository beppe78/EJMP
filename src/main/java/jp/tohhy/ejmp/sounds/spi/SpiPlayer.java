package jp.tohhy.ejmp.sounds.spi;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import jp.tohhy.ejmp.interfaces.AbstractMediaPlayer;
import jp.tohhy.ejmp.utils.PlayThread;
import jp.tohhy.ejmp.utils.PlayThread.Playable;

public abstract class SpiPlayer extends AbstractMediaPlayer {
    private SourceDataLine line;
    private Clip clip;
    private long totalBytes;
    private PlayThread playThread;
    protected final byte[] buffer = new byte[20480];
    
    
    public Clip getClip(SpiSound media) {
        if(clip == null || !clip.isOpen()) {
            try {
                clip = AudioSystem.getClip();
                clip.open(media.getStream());
                setTotalBytes(media.getStream().available());
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return clip;
    }
    
    public SourceDataLine getLine(SpiSound media) {
        try {
            if(line == null || !line.isOpen()) {
                final AudioFormat format = media.getStream().getFormat();
                final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format, buffer.length);
                setTotalBytes(media.getStream().available());
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }
    
    public void createThread(Playable p) {
        playThread = new PlayThread(p);
    }
    
    public void startThread() {
        if(playThread != null)
            playThread.start();
    }
    
    public void disposeThread() {
        if(playThread != null)
            playThread.setEnd(true);
        playThread = null;
    }

}
