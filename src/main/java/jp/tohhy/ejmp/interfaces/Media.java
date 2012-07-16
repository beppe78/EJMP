package jp.tohhy.ejmp.interfaces;

import java.io.File;

public abstract class Media {
    private final File file;
    private final String resourcePath;

    public enum MediaType {
        WAVE, AIFF, AU,
        MP3, AAC, OGG, WMA,
        MIDI,
        AVI, FLV
    }

    /**
     * 内部リソースのパスを指定してメディアを読み込む.
     * @param resourcePath
     */
    public Media(String resourcePath) {
        this.file = null;
        this.resourcePath = resourcePath;
    }

    /**
     * 外部ファイルを指定してメディアを読み込む.
     * @param file
     */
    public Media(File file) {
        this.file = file;
        this.resourcePath = null;
    }

    /**
     * このメディアをファイルやリソースから再読み込みする.
     * ストリームは初期化される.
     */
    public abstract void reload();

    /**
     * このメディアの形式を返す.
     * @return このメディアの形式
     */
    public abstract MediaType getMediaType();

    /**
     * このメディアを破棄する.関連するストリームをすべて閉じるように実装する.
     */
    public abstract void dispose() throws Exception;

    /**
     * このメディアを表現する名前を返す.
     * 曲名表示やリスト中の表示に用いられることを想定、
     * toStringはデフォルトでこのメソッドを呼び出す
     * 適宜overrideして適切な値を返すようにする
     * @return ファイル名や楽曲名
     */
    public String getName() {
        if(file != null)
            return file.getName();
        if(resourcePath != null)
            return resourcePath;
        return toString();
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * このメディアがファイルから読み出されていればそのファイルを返す.
     * そうでなければnullを返す.
     */
    public File getFile() {
        return file;
    }

    /**
     * このメディアがリソースから読み出されていればリソースのパスを返す.
     * そうでなければnullを返す.
     */
    public String getResourcePath() {
        return resourcePath;
    }

}
