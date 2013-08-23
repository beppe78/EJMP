package info.olivinecafe.ejmp.sounds;

import info.olivinecafe.ejmp.interfaces.AbstractMediaPlayer;
import info.olivinecafe.ejmp.interfaces.Media;
import info.olivinecafe.ejmp.interfaces.MediaPlayer;
import info.olivinecafe.ejmp.utils.MediaLocation;
import info.olivinecafe.ejmp.utils.MediaUtils;
import info.olivinecafe.ejmp.utils.PlayerUtils;


/**
 * 音楽再生用のプレイヤー.
 * setMediaでファイルやリソースを与えてplay()を呼び出すと、
 * メディアの拡張子から判断してその形式に対し適切なプレイヤーを内部で生成して実行する.
 */
@SuppressWarnings("rawtypes")
public class SoundPlayer extends AbstractMediaPlayer {
    private MediaPlayer player;
    private double volume = 1.0;
    private double pan = 0.0;
    private boolean isFading = false;
    private double fadeVolume = 1.0;

    public SoundPlayer() {}

    public void setMedia(MediaLocation location) {
        setMedia(MediaUtils.createSuitableMedia(location));
    }
    
    public void setMedia(Media media) {
        preparePlayer(media);
    }
    
    public Media getMedia() {
        if(player != null)
            return player.getMedia();
        return null;
    }

    public void setLoop(boolean isRepeat) {
        super.setLoop(isRepeat);
        if(player != null)
            player.setLoop(isRepeat);
    }
    
    @SuppressWarnings("unchecked")
    private void preparePlayer(Media media) {
        if(media == null) {
            System.err.println("preparePlayer: received media is null");
            return;
        }
        if(player == null) {
            if(player != null) player.stop();
            player = PlayerUtils.createSuitablePlayer(media);
        }
        if(player != null) {
            if(player.getMedia() != media)
                player.setMedia(media);
            player.setLoop(isLoop());
            player.setVolume(volume);
        }
    }

    /**
     * メディアを再生する.
     * 途中で停止されている場合はその停止位置から再開する.
     */
    public void play() {
        preparePlayer(getMedia());
        player.play();
    }

    /**
     * メディアの再生を停止する.
     * 停止後にplayを呼び出した場合、停止した時点から再開できる.
     */
    public void stop() {
        if(player != null)
            player.stop();
    }

    public void rewind() {
        if(player != null)
            player.rewind();
    }

    public boolean isPlaying() {
        if(player != null)
            return player.isPlaying();
        return false;
    }

    public double getVolume() {
        if(player != null)
            return player.getVolume();
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        if(player != null)
            player.setVolume(volume);
    }

    public double getFadeVolume() {
        return fadeVolume;
    }

    public boolean isFading() {
        return isFading;
    }
    
    public void fade(double toVolume, int timeMs) {
        if(!isFading) {
            new Fade(this, toVolume, timeMs);
        }
    }
    
    public void fade(double toVolume, int timeMs, Runnable endAction) {
        if(!isFading) {
            new Fade(this, toVolume, timeMs, endAction);
        }
    }
    
    /**
     * ボリューム0から現在のボリュームに向かってフェードインする再生を開始する.
     */
    public void fadeIn(int timeMs) {
        if(!isFading) {
            stop();
            final double toVolume = volume;
            this.setVolume(0);
            play();
            fade(toVolume, timeMs);
        }
    }
    
    /**
     * 現在のボリュームからボリューム0に向かってフェードアウトする再生を開始する.
     * フェードアウト後は自動で停止し、ボリュームはフェードアウト前のボリュームに変更される.
     */
    public void fadeOut(int timeMs) {
        if(!isFading) {
            final double currentVolume = volume;
            play();
            fade(0, timeMs, new Runnable() {
                public void run() {
                    stop();
                    volume = currentVolume;
                }
            });
        }
    }
    
    /**
     * フェードを途中で停止する.終了時アクションは実行されない.
     * プレイヤーの音量はその時点のフェード音量になる.
     */
    public void stopFade() {
        this.isFading = false;
        this.volume = fadeVolume;
    }

    protected void setFadeVolume(double fadeVolume) {
        this.fadeVolume = fadeVolume;
        if(isFading)
            player.setVolume(fadeVolume);
    }

    protected void setFading(boolean isFading) {
        this.isFading = isFading;
    }

    public double getPan() {
        if(player != null)
            return player.getPan();
        return this.pan;
    }

    public void setPan(double pan) {
        this.pan = pan;
        if(player != null)
            player.setPan(pan);
    }
}
