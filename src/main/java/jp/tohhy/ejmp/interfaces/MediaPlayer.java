package jp.tohhy.ejmp.interfaces;


/**
 * 再生や停止が可能な何らかのメディアのプレイヤーが実装するインタフェース.
 * プレイヤーは適宜スレッドを用いてブロックせずに再生処理を行えるようにする.
 * @author tohhy
 */
public interface MediaPlayer {

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
     * メディアを頭まで巻き戻す.
     */
    public void rewind();

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
    public boolean isLoop();

    /**
     * リピートの有無を設定する.
     * @param isRepeat
     */
    public void setLoop(boolean isRepeat);
    
    /**
     * このプレイヤーの音量を取得する.
     * @return 1.0をデフォルト音量としての割合
     */
    public double getVolume();
    
    /**
     * このプレイヤーの音量を設定する.
     * @param volume 1.0をデフォルト音量としての割合
     */
    public void setVolume(double volume);
    
    /**
     * このプレイヤーの再生音の左右位置を取得する.
     * @return -1.0 (左チャネルのみ) 〜 1.0 (右チャネルのみ)
     */
    public double getPan();
    
    /**
     * このプレイヤーの再生音の左右位置を設定する.
     * @param pan -1.0 (左チャネルのみ) 〜 1.0 (右チャネルのみ)
     */
    public void setPan(double pan);
}
