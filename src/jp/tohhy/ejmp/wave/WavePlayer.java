package jp.tohhy.ejmp.wave;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

import jp.tohhy.ejmp.MediaPlayer;

public class WavePlayer implements MediaPlayer {
    private WaveSound playing;
    private Thread playThread = null;

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
                    AudioFormat format = playing.getAudio().getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    Clip line = (Clip) AudioSystem.getLine(info);
                    line.open(playing.getAudio());
                    line.start();

                    line.drain();
                    line.close();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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

    public File getMedia() {
        return playing.getFile();
    }

    public void restart() {
        playThread.start();
    }

    public void stop() {
     // TODO 自動生成されたメソッド・スタブ
    }

    public boolean isPlaying() {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }


}
