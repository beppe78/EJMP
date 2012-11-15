package jp.tohhy.ejmp.sounds;

import java.io.File;
import java.net.URL;

import jp.tohhy.ejmp.interfaces.AbstractMediaPlayer;
import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;
import jp.tohhy.ejmp.utils.MediaUtils;
import jp.tohhy.ejmp.utils.PlayerUtils;

/**
 * 音楽再生用のプレイヤー.
 * setMediaでファイルやリソースを与えてplay()を呼び出すと、
 * メディアの拡張子から判断してその形式に対し適切なプレイヤーを内部で生成して実行する.
 */
public class SoundPlayer extends AbstractMediaPlayer {
    private MediaPlayer player;

    public SoundPlayer() {}

    public void setMedia(File file) {
        setMedia(MediaUtils.createSuitableMedia(file));
    }

    public void setMedia(String resourcePath) {
        setMedia(MediaUtils.createSuitableMedia(resourcePath));
    }

    public void setMedia(URL url) {
        setMedia(MediaUtils.createSuitableMedia(url));
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
    
    private void preparePlayer(Media media) {
        if(player == null || player.getClass() != PlayerUtils.getSuitablePlayerClass(media)) {
            if(player != null) player.stop();
            player = PlayerUtils.createSuitablePlayer(media);
        }
        if(player != null) {
            if(player.getMedia() != media)
                player.setMedia(media);
            player.setLoop(isLoop());
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

}
