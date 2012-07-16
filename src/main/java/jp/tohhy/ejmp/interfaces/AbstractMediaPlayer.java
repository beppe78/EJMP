package jp.tohhy.ejmp.interfaces;

import java.io.File;

import jp.tohhy.ejmp.utils.MediaUtils;

public abstract class AbstractMediaPlayer implements MediaPlayer {
    private Media media;
    private boolean isRepeat = false;

    public void setMedia(File file) {
        this.media = MediaUtils.createSuitableMedia(file);
    }

    public void setMedia(String resourcePath) {
        this.media = MediaUtils.createSuitableMedia(resourcePath);
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

}
