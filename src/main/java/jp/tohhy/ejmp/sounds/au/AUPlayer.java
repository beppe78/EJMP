package jp.tohhy.ejmp.sounds.au;

import java.applet.AudioClip;

import jp.tohhy.ejmp.interfaces.AbstractMediaPlayer;
import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;

public class AUPlayer extends AbstractMediaPlayer implements MediaPlayer {
    private AUSound media;

    public void play() {
        if(isLoop()) {
            AudioClip clip = getAUMedia().getClip();
            clip.loop();
        } else {
            AudioClip clip = getAUMedia().getClip();
            clip.play();
        }
    }

    public void restart() {
        stop();
        getAUMedia().reload();
        play();
    }

    /**
     * AU再生の実装上停止の度に巻き戻しがかかる.
     */
    public void stop() {
        if(getAUMedia() != null && getAUMedia().getClip() != null)
            getAUMedia().getClip().stop();
    }

    @Deprecated
    /**
     * 未実装.
     */
    public boolean isPlaying() {
        return false;
    }

    private AUSound getAUMedia() {
        return (AUSound)getMedia();
    }

    public void rewind() {
        // TODO 自動生成されたメソッド・スタブ
        
    }

    public void setMedia(Media media) {
        if(media instanceof AUSound)
            this.media = (AUSound)media;
    }

    public Media getMedia() {
        return media;
    }

    public double getVolume() {
        return 1.0;
    }

    public void setVolume(double volume) {}

}
