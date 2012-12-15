package jp.tohhy.ejmp.sounds.jmf;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.Player;
import javax.media.Time;

import jp.tohhy.ejmp.interfaces.AbstractMediaPlayer;
import jp.tohhy.ejmp.interfaces.Media;

public class JMFPlayer extends AbstractMediaPlayer implements ControllerListener {
    private Player player;
    private JMFSound playing;
    private boolean isPlaying = false;
    private double volume = 1.0;

    public void play() {
        //既に再生中かメディアが存在しなければ何もしない
        if(isPlaying || playing == null)
            return;
        //そうでなければ再生する
        isPlaying = true;
        //プレイヤーが存在しなければ生成
        if(player == null) {
            try {
                player = Manager.createRealizedPlayer(playing.getDataSource());
                player.addControllerListener(this);
                
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        //再生
        player.start();
    }

    public void rewind() {
        player.setMediaTime(new Time(0));
    }

    public void stop() {
        if(player != null)
            player.stop();
        isPlaying = false;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }

    public void setMedia(Media media) {
        if(media instanceof JMFSound)
            this.playing = (JMFSound) media;
    }

    public Media getMedia() {
        return playing;
    }

    public void controllerUpdate(ControllerEvent e) {
        if(e instanceof EndOfMediaEvent) {
            System.out.println("end");
            if(isLoop())
                restart();
        }
    }
    
    public void setVolume(double volume) {
        if(player != null) {
            //TODO
//            final FloatControl volumeControl = 
//                    (FloatControl)player.getControl(FloatControl.Type.MASTER_GAIN);
//            volumeControl.setValue((float)Math.log10(volume) * 20);
        }
        this.volume = volume;
    }
    
    public double getVolume() {
        return volume;
    }

    public double getPan() {
        // TODO
        return 0;
    }

    public void setPan(double pan) {
        // TODO
    }
}
