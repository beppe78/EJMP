package jp.tohhy.ejmp.sounds.midi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;

import jp.tohhy.ejmp.interfaces.AbstractMedia;

public class MIDISound extends AbstractMedia {
    private Receiver receiver;
    private Sequencer sequencer;

    public MIDISound(String resourcePath) {
        super(resourcePath);
        init(null);
    }
    public MIDISound(File file) {
        super(file);
        init(null);
    }

    public MediaType getMediaType() {
        return MediaType.MIDI;
    }

    public void dispose() {
        if(sequencer != null)
            sequencer.close();
        sequencer = null;
    }
    
    private void init(Receiver receiver) {
        try {
            final BufferedInputStream stream = new BufferedInputStream(getUrl().openStream());
            this.sequencer = MidiSystem.getSequencer(false);
            sequencer.setSequence(stream);
            if(receiver != null)
                sequencer.getTransmitter().setReceiver(receiver);
            sequencer.open();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void reload() {
        dispose();
        init(receiver);
    }
    
    protected void setReceiver(Receiver receiver) {
        this.receiver = receiver;
        if(receiver != null && sequencer != null)
            try {
                sequencer.getTransmitter().setReceiver(receiver);
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
    }

    public void setSequencer(Sequencer sequencer) {
        this.sequencer = sequencer;
    }

    public Sequencer getSequencer() {
        return sequencer;
    }
}
