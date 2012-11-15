package jp.tohhy.ejmp.interfaces;


public abstract class AbstractMediaPlayer implements MediaPlayer {
    private Media media;
    private boolean isRepeat = false;

    public void restart() {
        stop();
        rewind();
        play();
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
