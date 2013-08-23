package info.olivinecafe.ejmp.interfaces;

import java.io.IOException;
import java.net.URL;

public interface Media {
    
    public enum MediaType {
        WAVE, AIFF, AU,
        MP3, AAC, OGG, WMA,
        TAC, FLAC, TTA, APE, WAVPACK,
        MIDI,
        AVI, FLV
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
    public abstract void dispose() throws IOException;
    
    /**
     * このメディアを表現する名前を返す.
     * 曲名表示やリスト中の表示に用いられることを想定、
     * 適宜overrideして適切な値を返すようにする
     * @return ファイル名や楽曲名
     */
    public String getName();
    
    /**
     * このメディアを表現するURLを返す.
     * @return このメディアのURL
     */
    public URL getURL();

}