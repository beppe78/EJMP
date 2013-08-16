package jp.tohhy.ejmp.sounds.midi;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.Synthesizer;

public class MidiUtils {
    
    public static void applyVolume(Synthesizer synth, double volume) {
        final MidiChannel[] channels = synth.getChannels();
        final int VOLUME_CONTROLLER = 7; 
        int panValue = (int)(128*volume);
        for(MidiChannel channel:channels)
            channel.controlChange(VOLUME_CONTROLLER, panValue);
    }
    
    public static void applyPan(Synthesizer synth, double pan) {
        final MidiChannel[] channels = synth.getChannels();
        final int PAN_CONTROLLER = 10; 
        int panValue = (int)(128*pan);
        for(MidiChannel channel:channels)
            channel.controlChange(PAN_CONTROLLER, panValue);
    }
}
