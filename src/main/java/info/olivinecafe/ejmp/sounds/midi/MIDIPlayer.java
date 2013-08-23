package info.olivinecafe.ejmp.sounds.midi;

import info.olivinecafe.ejmp.sounds.spi.SpiPlayer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.midi.Synthesizer;


public class MIDIPlayer extends SpiPlayer<MIDISound> {

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

    /*
     * getter/setter
     */
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
