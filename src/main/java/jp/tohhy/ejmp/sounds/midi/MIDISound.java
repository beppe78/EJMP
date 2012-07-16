package jp.tohhy.ejmp.sounds.midi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.utils.StreamUtils;

public class MIDISound extends Media {
    private Sequencer sequencer;

    public MIDISound(String resourcePath) {
        super(resourcePath);
        try {
            loadSequence(resourcePath);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public MIDISound(File file) {
        super(file);
        try {
            loadSequence(file);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MediaType getMediaType() {
        return MediaType.MIDI;
    }

    @Override
    public void dispose() throws Exception {
        if(sequencer != null)
            sequencer.close();
    }

    @Override
    public void reload() {
        try {
            if(sequencer != null)
                sequencer.close();
            if(getFile() != null && getFile().exists()) {
                loadSequence(getFile());
            } else if(getResourcePath() != null) {
                loadSequence(getResourcePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void loadSequence(File file) throws InvalidMidiDataException, IOException {
        try {
            InputStream stream = StreamUtils.getFileAsStream(file);
            this.sequencer = MidiSystem.getSequencer(false);
            sequencer.setSequence(stream);
            sequencer.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
    private void loadSequence(String resourcePath) throws InvalidMidiDataException, IOException {
        try {
            InputStream stream = StreamUtils.getResourceAsStream(resourcePath);
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
