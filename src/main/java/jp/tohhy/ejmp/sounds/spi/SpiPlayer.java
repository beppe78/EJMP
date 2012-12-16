package jp.tohhy.ejmp.sounds.spi;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import jp.tohhy.ejmp.interfaces.AbstractMediaPlayer;
import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.utils.PlayThread;
import jp.tohhy.ejmp.utils.PlayThread.Playable;

public abstract class SpiPlayer extends AbstractMediaPlayer {
    private SourceDataLine line;
    private boolean isPlaying = false;
    private double volume = 1.0;
    private double pan = 0.0;
    private PlayThread playThread;
    private int bufferSize = 20480;
    protected byte[] buffer = new byte[getBufferSize()];
    
    public abstract SpiSound getSpiSound();

    protected int readStream(SpiSound media, byte[] buffer) throws IOException {
        return media.getDecodedStream().read(buffer, 0, buffer.length);
    }

    private void createPlayThread(final SpiSound media) {
        createThread(new Playable() {
            public void play(PlayThread thread) {
                final SourceDataLine line = getLine(media);
                line.start();
                isPlaying = true;
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
                isPlaying = false;
                //終端で終了していた場合はループする
                if(isLoop() && currentRead == -1) {
                    restart();
                }
                //lineが再生し終わるまでスレッドを保持
                //これを入れないとstop直後にプレイヤーが停止していることが保証されない
                line.drain();
            }
        });
    }

    private void startThread() {
        if(playThread != null) {
            playThread.start();
        }
    }

    private void createThread(Playable p) {
        playThread = new PlayThread(p);        
    }

    private void disposeThread() {
        //再生中なら止めてスレッドを終了させる
        if(isPlaying && playThread != null) {
            playThread.setEnd(true);
            //スレッドが終了するまでウェイト
            try {
                playThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        playThread = null;
    }

    private SourceDataLine getLine(SpiSound media) {
        try {
            if(line == null || !line.isOpen()) {
                final AudioFormat format = media.getFormat();
                final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                line = (SourceDataLine) AudioSystem.getLine(info);
                if(buffer.length != getBufferSize())
                    buffer = new byte[getBufferSize()];
                line.open(format, buffer.length);
                setVolume(volume);
                setPan(pan);
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return line;
    }

    public void play() {
        if(getMedia() != null && !isPlaying()) {
            createPlayThread(getSpiSound());
            startThread();
        }
    }
    
    public void stop() {
        disposeThread();
        this.isPlaying = false;
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
    
    public void setVolume(double volume) {
        if(line != null && line.isOpen()) {
            final FloatControl volumeControl = 
                    (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
            float value = (float)Math.log10(volume) * 20;
            volumeControl.setValue(value);
        }
        this.volume = volume;
    }
    
    public void setPan(double pan) {
        if(line != null && line.isOpen()) {
            final FloatControl panControl = 
                    (FloatControl)line.getControl(FloatControl.Type.PAN);
            float value = (float)pan;
            if(value > 1.0) value = 1.0f;
            if(value < -1.0) value = -1.0f;
            panControl.setValue(value);
        }
        this.pan = pan;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }

    public double getVolume() {
        return volume;
    }
    
    public double getPan() {
        return pan;
    }

    public Media getMedia() {
        return getSpiSound();
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}
