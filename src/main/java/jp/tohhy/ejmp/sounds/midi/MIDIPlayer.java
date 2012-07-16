package jp.tohhy.ejmp.sounds.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;

import com.sun.media.sound.SF2Soundbank;
import com.sun.media.sound.SoftSynthesizer;

public class MIDIPlayer implements MediaPlayer {
    private Sequencer sequencer;
    private Synthesizer synth;
    private MIDISound playing;
    private boolean isRepeat = false;
    private int volume = 100;

    public MIDIPlayer() {
        try {
            refreshPlayer(new SoftSynthesizer());
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            if(playing != null) {
                if(!sequencer.isOpen())
                    sequencer.open();
                sequencer.setSequence(getPlaying().getSequence());
                sequencer.start();
                Thread.sleep(100);
                setVolume(volume);
            }
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void restart() {
        if(sequencer.isOpen()) {
            sequencer.stop();
            sequencer.setTickPosition(0);
        }
        play();
    }

    public void stop() {
        sequencer.stop();
    }

    public boolean isPlaying() {
        return sequencer.isRunning();
    }

    public void setMedia(File file) {
        this.playing = new MIDISound(file);
    }

    public void setMedia(String resourcePath) {
        this.playing = new MIDISound(resourcePath);
    }

    public void setMedia(Media media) {
        if(media instanceof MIDISound)
            this.playing = (MIDISound) media;
    }

    public Media getMedia() {
        return playing;
    }

    /**
     * 指定したシンセサイザを用いてプレイヤーを再構築する.
     *
     * 読み込み済みのサウンドフォント等を破棄し、GCをかけてメモリを解放する.
     * その後新たにsequencerとsynthを再構築し、openする
     * @throws MidiUnavailableException
     */
    protected void refreshPlayer(Synthesizer synth) throws MidiUnavailableException {
        if(this.synth != null) {
            this.synth.close();
            this.synth = null;
        }
        if(this.sequencer != null) {
            this.sequencer.close();
            this.sequencer = null;
        }
        System.gc();
        this.synth = synth;
        this.sequencer = MidiSystem.getSequencer(false);
        this.sequencer.getTransmitter().setReceiver(this.synth.getReceiver());
        this.synth.open();
        this.sequencer.open();
    }

    /**
     * サウンドフォントを適用する.
     * @param soundfont .sf2形式のファイル
     */
    public void loadSountFont(File soundfont) {
        //SoundBankはOpen後に指定する
        try {
            refreshPlayer(new SoftSynthesizer());
            SF2Soundbank bank = new SF2Soundbank(soundfont);
            synth.unloadAllInstruments(synth.getDefaultSoundbank());
            synth.loadAllInstruments(bank);
            bank = null;
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定したシンセサイザを適用する.
     * @param synth
     */
    public void loadSynthesizer(Synthesizer synth) {
        try {
            refreshPlayer(synth);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }



    /*
     * getter/setter
     */

    public void setTick(long tick) {
        this.sequencer.setTickPosition(tick);
    }

    public long getTick() {
        return this.sequencer.getTickPosition();
    }


    public void setRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
        MidiUtils.applyVolume(synth, volume);
    }

    public void setPlaying(MIDISound playing) {
        if(sequencer.isOpen()) {
            sequencer.stop();
            sequencer.setTickPosition(0);
        }
        this.playing = playing;
    }

    public MIDISound getPlaying() {
        return playing;
    }

    public void run() {
        // TODO 自動生成されたメソッド・スタブ
    }
}
