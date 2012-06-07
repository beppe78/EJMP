package jp.tohhy.ejmp;

import java.io.File;
import java.net.URISyntaxException;

public abstract class Media {
    private File file = null;

    public enum MediaType {
        WAVE, MP3, OGG, MIDI,
        AVI, FLV
    }

    public Media() {}

    /**
     * 内部リソースのパスを指定してメディアを読み込む.
     * @param resourcePath
     */
    public Media(String resourcePath) {
        try {
            this.file = new File(getClass().getClassLoader().getResource(resourcePath).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Media(File file) {
        this.file = file;
    }

    /**
     * このメディアの形式を返す.
     * @return このメディアの形式
     */
    public abstract MediaType getMediaType();

    /**
     * この音を表現する名前を返す.
     * 曲名表示やリスト中の表示、toStringに用いられることを想定.
     * @return ファイル名や楽曲名
     */
    public String getName() {
        return file.getName();
    }

    /**
     * このメディアが保存された実体ファイルが存在すればそのファイルを返す.
     * 存在しなければnullを返す.
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     * このメディアが保存された実体ファイルを指定する.
     */
    public void setFile(File file) {
        this.file = file;
    }

}
