package jp.tohhy.ejmp.sounds.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.SysexMessage;

public class MidiUtils {

    /**
     * シンセサイザにボリューム設定を適用する.
     * 有効な音量は０〜１２７、Sysexを叩いているだけなので再生ごとに再度呼び出すこと
     */
    public static void applyVolume(Synthesizer synth, int volume) {
        //有効な音量は0〜127
        if(volume > 127) {
            volume = 127;
        } else if(volume < 0){
            volume = 0;
        }
        //音量を16進数の文字列表現に直す
        String volStr = Integer.toHexString(volume);
        if(volStr.length() == 1) {
            volStr = "0" + volStr;
        }
        /*
         * マスターボリュームを変更するメッセージ文字列の生成、byte化
         * 参考:
         * http://www.tim.hi-ho.ne.jp/t-inukai/mkgmdata.html#mvolume
         */
        String strMessage = "F07F7F0401" + volStr + volStr + "F7";
        int nLengthInBytes = strMessage.length() / 2;
        byte[]  abMessage = new byte[nLengthInBytes];
        for (int i = 0; i < nLengthInBytes; i++) {
            String numstr = strMessage.substring(i * 2, i * 2 + 2);
            abMessage[i] = (byte) Integer.parseInt(numstr, 16);
        }

        /*
         * メッセージ本体の生成、シンセサイザへの送信
         * 参考:
         * http://www.jsresources.org/examples/SendSysex.html
         * http://www.jsresources.org/examples/SendSysex.java.html
         */
        try {
            final SysexMessage sysexMessage = new SysexMessage();
            sysexMessage.setMessage(abMessage, abMessage.length);
            synth.getReceiver().send(sysexMessage, -1);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
}
