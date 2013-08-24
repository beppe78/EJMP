package info.olivinecafe.ejmp.media;

public abstract class AbstractMediaPlayer<T extends Media> implements MediaPlayer<T> {
    private boolean isLoop = false;

    public void restart() {
        stop();
        rewind();
        play();
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }
}
