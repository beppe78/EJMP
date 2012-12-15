package jp.tohhy.ejmp.sounds.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import jp.tohhy.ejmp.interfaces.AbstractMedia;
import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.interfaces.MediaPlayer;

import com.sun.media.sound.SF2Soundbank;
import com.sun.media.sound.SoftSynthesizer;

public class MIDIPlayer implements MediaPlayer {
    private Synthesizer synth;
    private MIDISound playing;
    private boolean isRepeat = false;
    private double volume = 1.0;

    public MIDIPlayer() {
        try {
            initPlayer(new SoftSynthesizer());
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定したシンセサイザを用いてプレイヤーを再構築する.
     *
     * 読み込み済みのサウンドフォント等を破棄し、GCをかけてメモリを解放する.
     * その後新たにsequencerとsynthを再構築し、openする
     * @throws MidiUnavailableException
     */
    protected void initPlayer(Synthesizer synth) throws MidiUnavailableException {
        if(this.synth != null) {
            this.synth.close();
            this.synth = null;
            System.gc();
        }
        this.synth = synth;
        if(this.playing != null) {
            playing.setReceiver(synth.getReceiver());
            playing.reload();
            playing.getSequencer().open();
        }
        this.synth.open();
    }

    public void play() {
        if(playing != null) {
            try {
                playing.setReceiver(synth.getReceiver());
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
            playing.getSequencer().start();
            MidiUtils.applyVolume(synth, 100);
        }
    }

    public void restart() {
        playing.reload();
        play();
    }

    public void stop() {
        if(playing != null && playing.getSequencer().isOpen())
            playing.getSequencer().stop();
    }

    public boolean isPlaying() {
        return playing.getSequencer().isRunning();
    }

    public void setMedia(File file) {
        this.playing = new MIDISound(file);
    }

    public void setMedia(String resourcePath) {
        this.playing = new MIDISound(resourcePath);
    }

    public AbstractMedia getMedia() {
        return playing;
    }

    /**
     * サウンドフォントを適用する.
     * @param soundfont .sf2形式のファイル
     */
    public void loadSountFont(File soundfont) {
        //SoundBankはOpen後に指定する
        try {
            initPlayer(new SoftSynthesizer());
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
            initPlayer(synth);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    /*
     * getter/setter
     */
    public void setTick(long tick) {
        this.playing.getSequencer().setTickPosition(tick);
    }

    public long getTick() {
        return this.playing.getSequencer().getTickPosition();
    }

    public void setLoop(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public boolean isLoop() {
        return isRepeat;
    }

    public double getVolume() {
        return volume;
    }

//    public void setVolume(int volume) {
//        this.volume = volume;
//        MidiUtils.applyVolume(synth, volume);
//    }

    public void setPlaying(MIDISound playing) {
        if(isPlaying()) {
            this.playing = playing;
            play();
        } else {
            this.playing = playing;
        }
    }

    public MIDISound getPlaying() {
        return playing;
    }

    public void run() {
        // TODO 自動生成されたメソッド・スタブ
    }

    public void rewind() {
        // TODO 自動生成されたメソッド・スタブ
        
    }

    public void setMedia(Media media) {
        if(media instanceof MIDISound)
            this.playing = (MIDISound) media;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPan() {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }

    public void setPan(double pan) {
        // TODO 自動生成されたメソッド・スタブ
        
    }
}
