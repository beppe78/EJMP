package info.olivinecafe.ejmp.sounds.spi;

import info.olivinecafe.ejmp.exceptions.StreamUnavailableException;
import info.olivinecafe.ejmp.sounds.AbstractSoundPlayer;
import info.olivinecafe.ejmp.sounds.filters.SoundFilter;
import info.olivinecafe.ejmp.utils.PlayThread;
import info.olivinecafe.ejmp.utils.PlayThread.Playable;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class SpiPlayer<SoundClass extends SpiSound> extends AbstractSoundPlayer<SoundClass> {
    private SourceDataLine line;
    private boolean isPlaying = false;
    private double volume = 1.0;
    private double pan = 0.0;
    private PlayThread playThread;
    private int bufferSize = 20480;
    private SoundClass media;
    protected byte[] buffer = new byte[getBufferSize()];
    
    protected int readStream(SpiSound media, byte[] buffer) throws StreamUnavailableException {
        try {
            int bytesRead = media.getDecodedStream().read(buffer, 0, buffer.length);
            if(bytesRead > 0)
                for(SoundFilter filter : getFilters())
                    filter.filter(buffer, 0, bytesRead, media.getFormat());
            return bytesRead;
        } catch (IOException e) {
            throw new StreamUnavailableException(e);
        }
    }

    private void createPlayThread(final SpiSound media) {
        createThread(new Playable() {
            public void play(PlayThread thread) {
                SourceDataLine line = getLine(media);
                line.start();
                isPlaying = true;
                int currentRead = 0;
                try {
                    while (isPlaying()) {
                        if(thread.isEnd()) break;
                        currentRead = readStream(media, buffer);
                        if (currentRead == -1) break;
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
                AudioFormat format = media.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
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
            createPlayThread(getMedia());
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
            if(value < volumeControl.getMaximum() && value > volumeControl.getMinimum())
                volumeControl.setValue(value);
        }
        this.volume = volume;
    }
    
    public void setPan(double pan) {
        if(line != null && line.isOpen()) {
            try {
                FloatControl panControl = 
                        (FloatControl)line.getControl(FloatControl.Type.PAN);
                float value = (float)pan;
                if(value > 1.0) value = 1.0f;
                if(value < -1.0) value = -1.0f;
                panControl.setValue(value);
            } catch (IllegalArgumentException e) {
                System.err.println("EJMP : pan unsupported");
            }
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

    public SoundClass getMedia() {
        return media;
    }

    public void setMedia(SoundClass media) {
        if(getMedia() != media) {
            this.media = media;
            rewind();
        }
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        if(bufferSize % 2 != 0)
            throw new IllegalArgumentException("buffer size should be an even number");
        this.bufferSize = bufferSize;
    }
}
