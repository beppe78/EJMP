package jp.tohhy.ejmp.interfaces;

import java.io.File;

/**
 * 再生や停止が可能な何らかのメディアのプレイヤーが実装するインタフェース.
 * プレイヤーは適宜スレッドを用いてブロックせずに再生処理を行えるようにする.
 * @author tohhy
 */
public interface MediaPlayer {
    /**
     * 再生するメディアをセットする.
     * @param file 再生するメディアが保存されたファイル
     */
    public void setMedia(File file);

    /**
     * 再生するメディアをセットする.
     * @param resourcePath 再生するメディアが保存されたリソースへのパス
     */
    public void setMedia(String resourcePath);

    /**
     * 再生するメディアをセットする.
     * @param file 再生するメディア
     */
    public void setMedia(Media media);

    /**
     * 再生中のメディアを取得する.
     * @return file 再生するメディアが保存されたファイル
     */
    public Media getMedia();

    /**
     * メディアを再生する.
     * 途中で停止されている場合はその停止位置から再開する.
     * new Thread(this)を生成してstartする処理を記述し、
     * 再生自体はrunメソッド内で行うようにする
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

    /**
     * リピートが設定されているかどうかを返す.
     * @return
     */
    public boolean isRepeat();

    /**
     * リピートの有無を設定する.
     * @param isRepeat
     */
    public void setRepeat(boolean isRepeat);

}
