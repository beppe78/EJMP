package info.olivinecafe.ejmp.sounds;

import java.util.ArrayList;
import java.util.List;

import info.olivinecafe.ejmp.media.AbstractMediaPlayer;
import info.olivinecafe.ejmp.sounds.filters.SoundFilter;
import info.olivinecafe.ejmp.sounds.spi.SpiSound;

public abstract class AbstractSoundPlayer<SoundClass extends SpiSound> 
    extends AbstractMediaPlayer<SoundClass> {
    

    private final List<SoundFilter> filters = new ArrayList<>();
    
    /**
     * このプレイヤーに適用されているフィルタの一覧を返す.
     * @return このプレイヤーに適用されているフィルタの一覧
     */
    public List<SoundFilter> getFilters() {
        return filters;
    }

}
