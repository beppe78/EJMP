package info.olivinecafe.ejmp.sounds;

import info.olivinecafe.ejmp.media.Media;
import info.olivinecafe.ejmp.media.MediaLocation;
import info.olivinecafe.ejmp.sounds.filters.SoundFilter;
import info.olivinecafe.ejmp.utils.GenericUtils;

/**
 * 音楽再生用のプレイヤー.
 * setMediaでファイルやリソースを与えてplay()を呼び出すと、
 * メディアの拡張子から判断してその形式に対し適切なプレイヤーを内部で生成して実行する.
 */
public class SoundPlayer extends AbstractSoundPlayer {
    private AbstractSoundPlayer player;

    /**
     * サウンドプレイヤーを初期化する.
     */
    public SoundPlayer() {}

    private void setPlayer(AbstractSoundPlayer player) {
        if(player != null) player.stop();
        this.player = player;
    }
    
    private AbstractSoundPlayer createNewPlayer(Media media) {
        if(media == null) {
            System.err.println("preparePlayer: received media is null");
            return null;
        }
        AbstractSoundPlayer result = 
                (AbstractSoundPlayer) GenericUtils.createSuitablePlayer(media);
        result.setMedia(media);
        applySettingsToPlayer(result);
        return result;
    }
    
    private void applySettingsToPlayer(AbstractSoundPlayer player) {
        if(player != null) {
            player.setLoop(isLoop());
            player.setVolume(getVolume());
            player.setPan(getPan());
            player.getFilters().clear();
            player.getFilters().addAll(getFilters());
        }
    }

    /**
     * メディアを再生する.
     * 途中で停止されている場合はその停止位置から再開する.
     */
    public void play() {
        if(player == null) {
            setPlayer(createNewPlayer(getMedia()));
        } else {
            applySettingsToPlayer(player);
        }
        player.play();
    }

    /**
     * メディアの再生を停止する.
     * 停止後にplayを呼び出した場合、停止した時点から再開できる.
     */
    public void stop() {
        if(player != null)
            player.stop();
    }

    public void rewind() {
        if(player != null)
            player.rewind();
    }

    @Override
    public void addFilter(SoundFilter filter) {
        super.addFilter(filter);
        if(player != null)
            player.getFilters().add(filter);
    }

    @Override
    public void clearFilter() {
        super.clearFilter();
        if(player != null)
            player.getFilters().clear();
    }

    /**
     * フェードを途中で停止する.終了時アクションは実行されない.
     * プレイヤーの音量はその時点のフェード音量になる.
     */
    public void stopFade() {
        setFading(false);
        setVolume(getFadeVolume());
    }

    
    /**
     * ボリューム0から現在のボリュームに向かってフェードインする再生を開始する.
     */
    public void fadeIn(int timeMs) {
        if(!isFading()) {
            double toVolume = getVolume();
            setVolume(0);
            play();
            new Fade(this, toVolume, timeMs);
        }
    }
    
    /**
     * 現在のボリュームからボリューム0に向かってフェードアウトする再生を開始する.
     * フェードアウト後は自動で停止し、ボリュームはフェードアウト前のボリュームに変更される.
     */
    public void fadeOut(int timeMs) {
        if(!isFading()) {
            final double currentVolume = getVolume();
            play();
            Runnable endAction = new Runnable() {
                public void run() {
                    stop();
                    setVolume(currentVolume);
                }
            };
            //フェードを実行
            new Fade(this, 0, timeMs, endAction);
        }
    }
    
    protected void setFadeVolume(double fadeVolume) {
        super.setFadeVolume(fadeVolume);
        if(isFading()) player.setVolume(fadeVolume);
    }

    public void setMedia(MediaLocation location) {
        setMedia(GenericUtils.createSuitableMedia(location));
    }

    public void setMedia(Media media) {
        setPlayer(createNewPlayer(media));
    }

    public Media getMedia() {
        if(player != null)
            return player.getMedia();
        return null;
    }

    public void setLoop(boolean isRepeat) {
        super.setLoop(isRepeat);
        if(player != null)
            player.setLoop(isRepeat);
    }

    public void setVolume(double volume) {
        super.setVolume(volume);
        if(player != null)
            player.setVolume(volume);
    }

    public void setPan(double pan) {
        super.setPan(pan);
        if(player != null)
            player.setPan(pan);
    }

    public boolean isPlaying() {
        if(player != null)
            return player.isPlaying();
        return false;
    }

}
