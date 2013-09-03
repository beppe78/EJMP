package info.olivinecafe.ejmp.media;

/**
 * 各メディアプレイヤーに共通した操作を定義する基底クラス.
 * カスタムする必要がある場合、各種メソッドをオーバーライドするようにする.
 * @author tohhy
 */
public abstract class AbstractMediaPlayer implements MediaPlayer {
    private boolean isLoop = false;
    private double volume = 1.0;
    private double pan = 0.0;

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
    
    public double getVolume() {
        return volume;
    }
    
    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPan() {
        return pan;
    }

    public void setPan(double pan) {
        this.pan = pan;
    }
}
