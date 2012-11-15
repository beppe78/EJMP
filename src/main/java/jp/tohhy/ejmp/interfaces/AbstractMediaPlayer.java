package jp.tohhy.ejmp.interfaces;


public abstract class AbstractMediaPlayer implements MediaPlayer {
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
