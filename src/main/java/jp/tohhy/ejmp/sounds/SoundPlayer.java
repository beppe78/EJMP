package jp.tohhy.ejmp.sounds;

import java.io.File;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;
import jp.tohhy.ejmp.utils.MediaUtils;
import jp.tohhy.ejmp.utils.PlayerUtils;

/**
 * 音楽再生用のプレイヤー.
 * setMediaでファイルやリソースを与えてplay()を呼び出すと、
 * メディアの拡張子から判断してその形式に対し適切なプレイヤーを内部で生成して実行する.
 */
public class SoundPlayer implements MediaPlayer {
    private Media media;
    private MediaPlayer player;
    private boolean isRepeat = false;

    public SoundPlayer() {}

    /**
     * 再生するメディアファイルを指定する.
     */
    public void setMedia(File file) {
        this.media = MediaUtils.createSuitableMedia(file);
    }
    /**
     * 再生するメディアリソースのパスを指定する.
     */
    public void setMedia(String resourcePath) {
        this.media = MediaUtils.createSuitableMedia(resourcePath);
    }
    /**
     * 再生するメディアを指定する.
     */
    public void setMedia(Media media) {
        this.media = media;
    }
    /**
     * このプレイヤーにセットされているメディアを取得する.
     */
    public Media getMedia() {
        return media;
    }

    /**
     * メディアを再生する.
     * 途中で停止されている場合はその停止位置から再開する.
     */
    public void play() {
        if(player == null || player.getClass() != PlayerUtils.getSuitablePlayerClass(media)) {
            if(player != null) player.stop();
            player = PlayerUtils.createSuitablePlayer(media);
        }
        if(player.getMedia() != media)
            player.setMedia(media);
        player.setRepeat(isRepeat);
        player.play();
    }

    public void restart() {
        if(player != null)
            player.restart();
    }

    /**
     * メディアの再生を停止する.
     * 停止後にplayを呼び出した場合、停止した時点から再開できる.
     */
    public void stop() {
        if(player != null)
            player.stop();
    }

    public boolean isPlaying() {
        if(player != null)
            return player.isPlaying();
        return false;
    }

    public boolean isRepeat() {
        if(player != null)
            return isRepeat;
        return false;
    }

    public void setRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
        if(player != null)
            player.setRepeat(isRepeat);
    }

}
