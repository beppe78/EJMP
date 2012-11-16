package jp.tohhy.ejmp.sounds.wave;

import java.io.IOException;

import javax.sound.sampled.SourceDataLine;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.utils.PlayThread;
import jp.tohhy.ejmp.utils.PlayThread.Playable;

public class WavePlayer extends SpiPlayer {
    private WaveSound media;
    private boolean isPlaying = false;

    public void setMedia(Media media) {
        if(media instanceof WaveSound)
            this.media = (WaveSound)media;
    }

    public Media getMedia() {
        return media;
    }

    public void play() {
        if(media != null && !isPlaying()) {
            createThread(new Playable() {
                public void play(PlayThread thread) {
                    final SourceDataLine line = getLine(media);
                    line.start();
                    isPlaying = true;
                    try {
                        long readBytes = 0;
                        while (readBytes < getTotalBytes() && isPlaying()) {
                            if(thread.isEnd()) break;
                            int read = media.getStream().read(buffer, 0, buffer.length);
                            if (read == -1) break;
                            readBytes += read;
                            line.write(buffer, 0, read);
                        }
                    } catch (IOException e) {
                          e.printStackTrace();
                    }
                    isPlaying = false;
                    if(isLoop()) {
                        restart();
                    }
                }
            });
            startThread();
        }
    }

    public void rewind() {
        if(isPlaying) {
            stop();
            media.reload();
            play();
        } else {
            stop();
            media.reload();
        }
    }

    public void stop() {
        disposeThread();
        isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}