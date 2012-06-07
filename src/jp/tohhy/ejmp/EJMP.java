package jp.tohhy.ejmp;

import jp.tohhy.ejmp.midi.MIDIPlayer;
import jp.tohhy.ejmp.wave.WavePlayer;

public class EJMP {


    public static MIDIPlayer createMidiPlayer() {
        return new MIDIPlayer();
    }

    public static WavePlayer createWavePlayer() {
        return new WavePlayer();
    }

    protected EJMP() {}

}
