package jp.tohhy.ejmp.sounds.mp3;

import java.io.File;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;

public class Mp3Player implements MediaPlayer, Runnable {
    private Player player;
    private Mp3Sound playing;
    private Thread thread;
    private boolean isRepeat = false;
    private boolean isStart = false;
    private boolean isStopped = false;

    public void setMedia(File file) {
        this.playing = new Mp3Sound(file);
        disposePlayer();
    }

    public void setMedia(String resourcePath) {
        this.playing = new Mp3Sound(resourcePath);
        disposePlayer();
    }

    public void setMedia(Media media) {
        if(media instanceof Mp3Sound) {
            this.playing = (Mp3Sound) media;
            disposePlayer();
        }
    }
    private void disposePlayer() {
        if(this.player != null) {
            this.player.close();
            this.player = null;
        }
    }

    public Media getMedia() {
        return playing;
    }

    public void play() {
        if(player == null) {
            try {
                player = new Player(playing.getStream());
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
        isStart = true;
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void restart() {
        // TODO 自動生成されたメソッド・スタブ
    }

    public void stop() {
        isStopped = true;
    }

    public boolean isPlaying() {
        return false;
    }

    public void run() {
        try {
            while(true) {
                if(isStart) {
                    isStart = false;
                    player.play();
                }
                if(isStopped) {
                    break;
                }
                if(player != null && player.isComplete()) {
                    //曲が演奏し終わったらリピートの有無を判定
                    //リピートフラグが立っていなければスレッドを終了
                    if(isRepeat) {
                        playing.streamReload();
                        player = new Player(playing.getStream());
                        player.play();
                    } else {
                        break;
                    }
                }
            }
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public boolean isRepeat() {
        return this.isRepeat;
    }

    public void setRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

}
