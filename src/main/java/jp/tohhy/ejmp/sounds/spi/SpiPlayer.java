package jp.tohhy.ejmp.sounds.spi;

import java.io.IOException;
import java.lang.Thread.State;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import jp.tohhy.ejmp.interfaces.AbstractMediaPlayer;
import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.utils.PlayThread;
import jp.tohhy.ejmp.utils.PlayThread.Playable;

public abstract class SpiPlayer extends AbstractMediaPlayer {
    private SourceDataLine line;
    private Clip clip;
    private boolean isPlaying = false;
    private long totalBytes;
    private PlayThread playThread;
    protected final byte[] buffer = new byte[20480];
    
    public abstract SpiSound getSpiSound();
    
    public Media getMedia() {
        return getSpiSound();
    }
    
    public void play() {
        if(getMedia() != null && !isPlaying()) {
            createPlayThread(getSpiSound());
            startThread();
        }
    }
    
    public void stop() {
        disposeThread();
        setPlaying(false);
    }
    
    public void rewind() {
        if(isPlaying()) {
            stop();
            getMedia().reload();
            play();
        } else {
            stop();
            getMedia().reload();
        }
    }
    
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

    public long getMediaTotalBytes() {
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
        //再生中なら止めてスレッドを終了させる
        if(isPlaying && playThread != null) {
            playThread.setEnd(true);
            //スレッドが終了するまでウェイト
            while(playThread.getState() != State.TERMINATED) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        playThread = null;
    }
    
    public void createPlayThread(final SpiSound media) {
        createThread(new Playable() {
            public void play(PlayThread thread) {
                final SourceDataLine line = getLine(media);
                line.start();
                setPlaying(true);
                int currentRead = 0;
                long readBytes = 0;
                try {
                    while (readBytes < getMediaTotalBytes() && isPlaying()) {
                        if(thread.isEnd()) 
                            break;
                        currentRead = media.getStream().read(buffer, 0, buffer.length);
                        if (currentRead == -1) 
                            break;
                        readBytes += currentRead;
                        line.write(buffer, 0, currentRead);
                    }
                } catch (IOException e) {
                      e.printStackTrace();
                }
                setPlaying(false);
                //終端で終了していた場合はループする
                if(isLoop() && readBytes >= getMediaTotalBytes()) {
                    restart();
                }
            }
        });
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

}
