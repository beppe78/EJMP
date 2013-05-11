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
import com.sun.media.sound.SF2Soundbank;
import com.sun.media.sound.SoftSynthesizer;

public class MIDISound extends SpiSound {
    private AudioInputStream stream;
    private Synthesizer synth;
    private Sequencer sequencer;
    private URL soundFont;

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
    public void init(Synthesizer synth) {
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
            final BufferedInputStream stream = new BufferedInputStream(getURL().openStream());
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
        try {
            super.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(sequencer != null) {
            if(sequencer.isOpen())
                sequencer.close();
            sequencer = null;
        }
        if(synth != null) {
            if(synth.isOpen())
                synth.close();
            synth = null;
            System.gc();
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

    public Sequencer getSequencer() {
        return sequencer;
    }
    
    public Synthesizer getSynth() {
        return synth;
    }
    
    public void setSynth(Synthesizer synth) {
        this.synth = synth;
        dispose();
        init(synth);
    }
    
    public void setSoundFont(URL soundfont) {
        try {
            //既存のシンセサイザを削除して新しいシンセサイザを適用しgc
            reload();
            if(synth != null && synth.isOpen()) {
                //SoundBankはsynth, receiverのOpen後に指定する必要がある
                final SF2Soundbank bank = new SF2Soundbank(soundfont);
                getSynth().unloadAllInstruments(getSynth().getDefaultSoundbank());
                getSynth().loadAllInstruments(bank);
                this.soundFont = soundfont;
            } else {
                System.out.println("synth closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.soundFont = null;
    }
    
    public URL getSoundFont() {
        return soundFont;
    }
}
