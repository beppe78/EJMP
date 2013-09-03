package info.olivinecafe.ejmp.sounds;

import info.olivinecafe.ejmp.media.AbstractMediaPlayer;
import info.olivinecafe.ejmp.sounds.filters.SoundFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 任意のサウンドプレーヤの基底クラス.
 * @author tohhy
 */
public abstract class AbstractSoundPlayer extends AbstractMediaPlayer {
    private final List<SoundFilter> filters = new ArrayList<>();
    private boolean isFading = false;
    private double fadeVolume = 1.0;
    
    /**
     * このプレイヤーに適用されているフィルタの一覧を返す.
     * @return このプレイヤーに適用されているフィルタの一覧
     */
    public List<SoundFilter> getFilters() {
        return this.filters;
    }
    
    /**
     * サウンドフィルタを追加する.
     * @param filter
     */
    public void addFilter(SoundFilter filter) {
        this.filters.add(filter);
    }
    
    /**
     * このプレイヤーが保持しているサウンドフィルタを全て破棄する.
     */
    public void clearFilter() {
        this.filters.clear();
    }
    
    /**
     * フェードに用いる音量を取得する.
     * @return フェードに用いる音量
     */
    protected double getFadeVolume() {
        return fadeVolume;
    }
    
    /**
     * フェードに用いる音量を設定する.
     * @param fadeVolume フェードに用いる音量
     */
    protected void setFadeVolume(double fadeVolume) {
        this.fadeVolume = fadeVolume;
    }
    
    /**
     * 現在フェードイン・アウトが進行中かどうかを返す.
     * @return フェードイン・アウトが進行中かどうか
     */
    public boolean isFading() {
        return isFading;
    }
    
    /**
     * フェードイン・アウトが進行中かどうかを設定する.
     * フェードの進行中は必ずフラグを立て、終わったら折るように実装する.
     * @param isFading フェードイン・アウトが進行中かどうか
     */
    protected void setFading(boolean isFading) {
        this.isFading = isFading;
    }
}
