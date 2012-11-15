package jp.tohhy.ejmp.sounds.mp3;

import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;
import jp.tohhy.ejmp.utils.PlayThread;
import jp.tohhy.ejmp.utils.PlayThread.Playable;

public class Mp3Player implements MediaPlayer {
    private Player player;
    private Mp3Sound playing;
    private PlayThread thread;
    private boolean isRepeat = false;
    private boolean isPlaying = false;
    private int stoppedLastByte = 0;

    private Playable threadPlay = new Playable() {
        public void play(final PlayThread thread) {
            final Mp3Sound playing = Mp3Player.this.playing;
            if(player != null) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            player.play();
                        } catch (JavaLayerException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                while(true) {
                    if(player == null || thread.isEnd()) {
                        break;
                    }
                    int available = 0;
                    try {
                        available = playing.getStream().available();
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                    if(player != null && available <= 0) {
                        //曲が演奏し終わったらリピートの有無を判定
                        //リピートフラグが立っていればまた別スレッドで再生、このスレッドは終了
                        if(isRepeat)
                            restart();
                        break;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    public void setMedia(Media media) {
        if(media instanceof Mp3Sound) {
            this.playing = (Mp3Sound) media;
            stop();
        }
    }

    public Media getMedia() {
        return playing;
    }

    public void play() {
        if(isPlaying)
            return;
        new Thread(new Runnable() {
            public void run() {
                if(player == null) {
                    try {
                        int full = playing.getStream().available();
                        int skiplength = full - stoppedLastByte;
                        while(skiplength > 0 && skiplength < full)
                            skiplength -= playing.getStream().skip(skiplength);
                        player = new Player(playing.getStream());
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                isPlaying = true;
                thread = new PlayThread(threadPlay);
                thread.start();
            }
        }).start();
    }

    public void restart() {
        stop();
        stoppedLastByte = Integer.MAX_VALUE;
        play();
    }

    public void stop() {
        try {
            if(playing != null) {
                stoppedLastByte = playing.getStream().available();
            }
            if(thread != null)
                thread.setEnd(true);
            if(this.player != null) {
                this.player.close();
                this.player = null;
            }
            playing.reload();
            isPlaying = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isLoop() {
        return this.isRepeat;
    }

    public void setLoop(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public void rewind() {
        stoppedLastByte = Integer.MAX_VALUE;
    }
}
