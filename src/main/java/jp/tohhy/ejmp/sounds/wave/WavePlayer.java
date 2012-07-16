package jp.tohhy.ejmp.sounds.wave;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;

public class WavePlayer implements MediaPlayer {
    private WaveSound playing;
    private Thread playThread = null;
    private boolean isPreLoad = false;
    private boolean isRepeat = false;
    private boolean isStopped = false;

    public WavePlayer() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    public void setPlaying(WaveSound playing) {
        this.playing = playing;
    }

    public WaveSound getPlaying() {
        return playing;
    }

    public void play() {
        playThread = new Thread(new Runnable() {
            public void run() {
                try {
                    final AudioFormat format = playing.getAudio().getFormat();
                    //pattern 1
                    if(isPreLoad) {
                        Clip clip = AudioSystem.getClip();
                        try {
                            clip.open(playing.getAudio());
                            clip.start();
                        } finally {
                            clip.drain();
                            clip.close();
                        }
                    } else {
                        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                        SourceDataLine line =  (SourceDataLine) AudioSystem.getLine(info);
                        byte[] buffer = new byte[65536];

                        try {
                            line.open(format, buffer.length);
                            line.start();

                            int numBytesRead = 0;
                            while (numBytesRead != -1) {
                                if(isStopped) {
                                    break;
                                }
                                numBytesRead = playing.getAudio().read(buffer, 0, buffer.length);
                                if (numBytesRead != -1) {
                                    line.write(buffer, 0, numBytesRead);
                                }
                            }
                        } finally {
                            line.drain();
                            line.close();
                        }

                    }
                    if(!isStopped && isRepeat)
                        play();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        );
        playThread.start();
    }

    public void setMedia(File file) {
        playing = new WaveSound(file);
    }

    public void setMedia(String resourcePath) {
        playing = new WaveSound(resourcePath);
    }

    public void setMedia(Media media) {
        if(media instanceof WaveSound)
            playing = (WaveSound)media;
    }

    public Media getMedia() {
        return playing;
    }

    public void restart() {
        playThread.start();
    }

    public void stop() {
        this.isStopped = true;
    }

    public boolean isPlaying() {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    public void run() {
        // TODO 自動生成されたメソッド・スタブ

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
}
