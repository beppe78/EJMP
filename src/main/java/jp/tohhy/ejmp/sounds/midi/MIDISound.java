package jp.tohhy.ejmp.sounds.midi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.utils.StreamUtils;

public class MIDISound extends Media {
    private Sequence sequence = null;

    public MIDISound(String resourcePath) {
        super(resourcePath);
        InputStream stream = null;
        try {
            stream = StreamUtils.getResourceAsStream(resourcePath);
            this.sequence = MidiSystem.getSequence(stream);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public MIDISound(File file) {
        super(file);
        InputStream stream = null;
        try {
            stream = StreamUtils.getFileAsStream(file);
            this.sequence = MidiSystem.getSequence(
                    stream);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
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

    @Override
    public void dispose() throws Exception {
        //MIDIでは特に何も行わなくてよい
    }
}
