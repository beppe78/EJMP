package jp.tohhy.ejmp.midi;


import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import jp.tohhy.ejmp.Media;

public class MIDISound extends Media {
    private Sequence sequence = null;

    public MIDISound(String resourcePath) {
        super(resourcePath);
        try {
            this.setSequence(MidiSystem.getSequence(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)));
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MIDISound(File file) {
        super(file);
        try {
            this.setSequence(MidiSystem.getSequence(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(file.getAbsolutePath())));
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        if(getFile() != null)
            return getFile().getName();
        return null;
    }

    public MediaType getMediaType() {
        return MediaType.MIDI;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Sequence getSequence() {
        return sequence;
    }

}
