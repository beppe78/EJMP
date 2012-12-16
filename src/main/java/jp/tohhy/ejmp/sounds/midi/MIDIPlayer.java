package jp.tohhy.ejmp.sounds.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Synthesizer;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

import com.sun.media.sound.SF2Soundbank;
import com.sun.media.sound.SoftSynthesizer;

public class MIDIPlayer extends SpiPlayer {

    private MIDISound media;
    
    public MIDIPlayer() {
        setBufferSize(8192);
    }

    public void setMedia(Media media) {
        if(media instanceof MIDISound)
            this.media = (MIDISound)media;
    }

    @Override
    public SpiSound getSpiSound() {
        return media;
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
    public void loadSountFont(File soundfont) {
        //SoundBankはOpen後に指定する
        try {
            media.init(new SoftSynthesizer());
            final SF2Soundbank bank = new SF2Soundbank(soundfont);
            media.getSynth().unloadAllInstruments(media.getSynth().getDefaultSoundbank());
            media.getSynth().loadAllInstruments(bank);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        media.getSequencer().setTickPosition(tick);
    }

    public long getTick() {
        return media.getSequencer().getTickPosition();
    }
}
