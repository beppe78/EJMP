package jp.tohhy.ejmp.sounds.midi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import jp.tohhy.ejmp.sounds.spi.SpiSound;

import com.sun.media.sound.AudioSynthesizer;
import com.sun.media.sound.SoftSynthesizer;

public class MIDISound extends SpiSound {
    private AudioInputStream stream;
    private Synthesizer synth;
    private Sequencer sequencer;

    public MIDISound(String resourcePath) {
        super(resourcePath);
        init(new SoftSynthesizer());
    }
    public MIDISound(File file) {
        super(file);
        init(new SoftSynthesizer());
    }
    
    public MIDISound(URL url) {
        super(url);
        init(new SoftSynthesizer());
    }
    
    public MIDISound(URI uri) {
        super(uri);
        init(new SoftSynthesizer());
    }
    
    /**
     * 指定したシンセサイザを用いてプレイヤーを再構築する.
     *
     * 読み込み済みのサウンドフォント等を破棄し、GCをかけてメモリを解放する.
     * その後新たにsequencerとsynthを再構築し、openする
     * @throws MidiUnavailableException
     */
    private void init(Synthesizer synth) {
        this.dispose();
        try {
            this.synth = synth;
            if(synth instanceof AudioSynthesizer) {
                try {
                    stream = ((AudioSynthesizer)synth).openStream(null, null);
                } catch (MidiUnavailableException e) {
                    e.printStackTrace();
                }
            } else {
                if(synth != null)
                    System.err.println("given synth is not AudioSynthesizer");
                synth.open();
            }
            final BufferedInputStream stream = new BufferedInputStream(getUrl().openStream());
            this.sequencer = MidiSystem.getSequencer(false);
            sequencer.setSequence(stream);
            sequencer.getTransmitter().setReceiver(synth.getReceiver());
            sequencer.open();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void start() {
        if(sequencer != null) {
            try {
                sequencer.getTransmitter().setReceiver(synth.getReceiver());
                sequencer.start();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
    
    public MediaType getMediaType() {
        return MediaType.MIDI;
    }
    
    @Override
    public AudioFileFormat getFileFormat() {
        return new AudioFileFormat(Type.WAVE, 
                new AudioFormat(Encoding.PCM_SIGNED, 44100.0f, 16, 2, 4, 44100.0f, false), AudioSystem.NOT_SPECIFIED);
    }

    public void dispose() {
        super.dispose();
        if(this.synth != null) {
            this.synth.close();
            this.synth = null;
            System.gc();
        }
        if(sequencer != null) {
            sequencer.close();
            sequencer = null;
        }
    }
    
    public void reload() {
        dispose();
        init(new SoftSynthesizer());
    }
    
    @Override
    protected AudioInputStream getRawStream() {
        return stream;
    }

    public void setSequencer(Sequencer sequencer) {
        this.sequencer = sequencer;
    }

    public Sequencer getSequencer() {
        return sequencer;
    }
    public Synthesizer getSynth() {
        return synth;
    }
    public void setSynth(Synthesizer synth) {
        this.synth = synth;
    }
}
