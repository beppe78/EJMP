package jp.tohhy.ejmp.sounds.midi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import jp.tohhy.ejmp.interfaces.AbstractMedia;

public class MIDISound extends AbstractMedia {
    private Sequencer sequencer;

    public MIDISound(String resourcePath) {
        super(resourcePath);
        try {
            loadSequence(getUrl());
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public MIDISound(File file) {
        super(file);
        try {
            loadSequence(getUrl());
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MediaType getMediaType() {
        return MediaType.MIDI;
    }

    public void dispose() throws Exception {
        if(sequencer != null)
            sequencer.close();
    }

    public void reload() {
        try {
            if(sequencer != null)
                sequencer.close();
            loadSequence(getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    
    private void loadSequence(URL url) throws InvalidMidiDataException, IOException {
        try {
            InputStream stream = getUrl().openStream();
            this.sequencer = MidiSystem.getSequencer(false);
            sequencer.setSequence(stream);
            sequencer.open();
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
