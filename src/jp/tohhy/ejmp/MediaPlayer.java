package jp.tohhy.ejmp;

import java.io.File;

/**
 * 再生や停止が可能な何らかのメディアのプレイヤーが実装するインタフェース.
 * @author tohhy
 */
public interface MediaPlayer {
    /**
     * 再生するメディアをセットする.
     * @param file 再生するメディアが保存されたファイル
     */
    public void setMedia(File file);

    /**
     * 再生中のメディアを取得する.
     * @return file 再生するメディアが保存されたファイル
     */
    public File getMedia();

    /**
     * メディアを再生する.
     * 途中で停止されている場合はその停止位置から再開する.
     */
    public void play();

    /**
     * メディアを頭から再生する.
     */
    public void restart();

    /**
     * メディアの再生を停止する.
     * 停止後にplayを呼び出した場合、停止した時点から再開できるようにする.
     */
    public void stop();

    /**
     * メディアが現在再生中であるかどうかを返す.
     * @return
     */
    public boolean isPlaying();

}
