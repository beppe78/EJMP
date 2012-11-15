package jp.tohhy.ejmp.sounds.wave;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import jp.tohhy.ejmp.interfaces.AbstractMedia;
import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;
import jp.tohhy.ejmp.utils.PlayThread;
import jp.tohhy.ejmp.utils.PlayThread.Playable;

public class WavePlayer implements MediaPlayer {
    private WaveSound playing;
    private PlayThread thread = null;
    private boolean isPreLoad = false;
    private boolean isRepeat = false;
    private boolean isPlaying = false;
    
    private Playable threadPlay = new Playable() {
        public void play(PlayThread thread) {
            isPlaying = true;
            final AudioFormat format = playing.getAudio().getFormat();
            if(isPreLoad) {
                //短い音声ファイル等でプリロードするパターン.
                //長いwavファイルは容量が大きくなりがちなためこちらで読むとメモリを圧迫してしまう
                Clip clip = null;
                try {
                    clip = AudioSystem.getClip();
                    clip.open(playing.getAudio());
                    clip.start();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } finally {
                    clip.drain();
                    clip.close();
                }
            } else {
                //長い音声ファイル等でプリロードせずに少しずつ読み込んでいくパターン.
                //デフォルトはこちらにする
                final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                final byte[] buffer = new byte[65536];
                SourceDataLine line = null;
                try {
                    line = (SourceDataLine) AudioSystem.getLine(info);
                    line.open(format, buffer.length);
                    line.start();
                    int readBytes = 0;
                    while (readBytes != -1) {
                        if(thread.isEnd()) {
                            break;
                        }
                        readBytes = playing.getAudio().read(buffer, 0, buffer.length);
                        if (readBytes != -1) {
                            line.write(buffer, 0, readBytes);
                            line.drain();
                        }
                    }
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    line.close();
                }
            }

            //リピートなら繰り返す
            if(isPlaying && isRepeat) {
                WavePlayer.this.play();
            } else {
                stop();
            }
        }
    };
    public void setPlaying(WaveSound playing) {
        this.playing = playing;
    }

    public WaveSound getPlaying() {
        return playing;
    }

    public void play() {
        if(isPlaying)
            return;
        try {
            if(playing.getAudio().available() <= 0)
                playing.reload();
            thread = new PlayThread(threadPlay);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMedia(File file) {
        playing = new WaveSound(file);
    }

    public void setMedia(String resourcePath) {
        playing = new WaveSound(resourcePath);
    }

    public void setMedia(AbstractMedia media) {
        if(media instanceof WaveSound)
            playing = (WaveSound)media;
    }

    public AbstractMedia getMedia() {
        return playing;
    }

    public void restart() {
        stop();
        rewind();
        if(!isPlaying)
            play();
    }

    public void rewind() {
        playing.reload();
        thread.setEnd(true);
    }

    public void stop() {
        thread.setEnd(true);
        this.isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isRepeat() {
        return this.isRepeat;
    }

    public void setRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    /**
     * プリロードの有無を設定する.
     * trueにした場合、waveファイルは事前にキャッシュされてから再生される.
     * @param isPreLoad
     */
    public void setPreLoad(boolean isPreLoad) {
        this.isPreLoad = isPreLoad;
    }

    public boolean isPreLoad() {
        return isPreLoad;
    }

    public void setMedia(Media media) {
        // TODO 自動生成されたメソッド・スタブ
        
    }
}
