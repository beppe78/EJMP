package info.olivinecafe.ejmp.sounds.midi;

import info.olivinecafe.ejmp.sounds.spi.SpiPlayer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.midi.Synthesizer;

/**
 * SPI形式とのインタフェースの共存が上手くいかないので保留中.
 * Streamに書き出してからフィルタすると遅延で使い物にならないので、
 * シーケンスデータそのものを動的に加工してシーケンサまでは直通という形を取る必要がありそう
 * @author tohhy
 */
@Deprecated
public class MIDIPlayer extends SpiPlayer {

    private MIDISound media;
    
    public MIDIPlayer() {
        setBufferSize(10000);
    }

    @Override
    public void play() {
        media.start();
        super.play();
    }

    /**
     * サウンドフォントを適用する.
     * @param soundfont .sf2形式のファイル
     */
    public void setSoundFont(File soundfont) {
        try {
            setSoundFont(soundfont.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    
    public void setSoundFont(URL soundfont) {
        media.setSoundFont(soundfont);
    }

    /**
     * 指定したシンセサイザを適用する.
     * @param synth
     */
    public void loadSynthesizer(Synthesizer synth) {
        media.init(synth);
    }

    public void setTick(long tick) {
        if(media != null && media.getSequencer() != null)
            media.getSequencer().setTickPosition(tick);
    }

    public long getTick() {
        if(media != null && media.getSequencer() != null)
            return media.getSequencer().getTickPosition();
        return 0;
    }
    
    public long getTickLength() {
        if(media != null && media.getSequencer() != null)
            return media.getSequencer().getTickLength();
        return 0;
    }
}
