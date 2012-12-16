package jp.tohhy.ejmp.sounds.midi;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.sounds.spi.SpiPlayer;
import jp.tohhy.ejmp.sounds.spi.SpiSound;

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
//    public void loadSountFont(File soundfont) {
//        //SoundBankはOpen後に指定する
//        try {
//            initPlayer(new SoftSynthesizer());
//            SF2Soundbank bank = new SF2Soundbank(soundfont);
//            synth.unloadAllInstruments(synth.getDefaultSoundbank());
//            synth.loadAllInstruments(bank);
//            bank = null;
//        } catch (MidiUnavailableException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 指定したシンセサイザを適用する.
     * @param synth
     */
//    public void loadSynthesizer(Synthesizer synth) {
//        try {
//            initPlayer(synth);
//        } catch (MidiUnavailableException e) {
//            e.printStackTrace();
//        }
//    }

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
