package jp.tohhy.ejmp.sounds.spi;

import java.io.IOException;
import java.lang.Thread.State;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import jp.tohhy.ejmp.interfaces.AbstractMediaPlayer;
import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.utils.PlayThread;
import jp.tohhy.ejmp.utils.PlayThread.Playable;

public abstract class SpiPlayer extends AbstractMediaPlayer {
    private SourceDataLine line;
    private boolean isPlaying = false;

    private PlayThread playThread;
    protected final byte[] buffer = new byte[20480];
    
    public abstract SpiSound getSpiSound();

    public void createPlayThread(final SpiSound media) {
        createThread(new Playable() {
            public void play(PlayThread thread) {
                final SourceDataLine line = getLine(media);
                line.start();
                setPlaying(true);
                int currentRead = 0;
                try {
                    while (isPlaying()) {
                        if(thread.isEnd()) 
                            break;
                        currentRead = readStream(media, buffer);
                        if (currentRead == -1) 
                            break;
                        line.write(buffer, 0, currentRead);
                    }
                } catch (IOException e) {
                      e.printStackTrace();
                }
                setPlaying(false);
                //終端で終了していた場合はループする
                if(isLoop() && currentRead == -1) {
                    restart();
                }
            }
        });
    }

    protected int readStream(SpiSound media, byte[] buffer) throws IOException {
        return media.getStream().read(buffer, 0, buffer.length);
    }

    public void startThread() {
        if(playThread != null)
            playThread.start();
    }

    public void createThread(Playable p) {
        playThread = new PlayThread(p);
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
    
    public SourceDataLine getLine(SpiSound media) {
        try {
            if(line == null || !line.isOpen()) {
                final AudioFormat format = media.getFormat();
                final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format, buffer.length);
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return line;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

}
