package info.olivinecafe.ejmp.sounds.filters;

import javax.sound.sampled.AudioFormat;

/**
 * サウンドにかけるフィルタの基底クラス.
 * @author tohhy
 */
public abstract class SoundFilter {
    
    /**
     * サウンドを表すバイトデータをフィルタリングする.
     * @param rawData サウンドを表すバイトデータの配列
     * @param offset 開始オフセット、省略すると0
     * @param length 配列の長さ、省略するとstream.length
     * @param format サウンドの形式
     */
    public abstract void filter(byte[] rawData, int offset, int length, AudioFormat format);
    
    /**
     * サウンドを表すバイトデータをフィルタリングする.
     * @param rawData サウンドを表すバイトデータの配列
     * @param format サウンドの形式
     */
    public void filter(byte[] rawData, AudioFormat format) {
        filter(rawData, 0, rawData.length, format);
    }
    
    /**
     * Convenience method for getting a 16-bit sample from a byte array.
     * Samples should be in 16-bit, signed format.
     * http://www.java2s.com/Code/Java/Development-Class/Anexampleofplayingasoundwithanechofilter.htm
     */
    public static short getSample(byte[] rawData, int position, boolean isBigEndian) {
      return isBigEndian ? 
              (short)(((rawData[position] & 0xff) << 8) | (rawData[position + 1] & 0xff)) :
              (short)(((rawData[position + 1] & 0xff) << 8) | (rawData[position] & 0xff));
    }
    
    /**
     * Convenience method for setting a 16-bit sample in a byte array.
     * Samples should be in 16-bit, signed format.
     * http://www.java2s.com/Code/Java/Development-Class/Anexampleofplayingasoundwithanechofilter.htm
     */
    public static void setSample(byte[] rawData, int position, short sample, boolean isBigEndian) {
        if(isBigEndian) {
            // 0xff => 11111111
            // 10101010 11000011 -> 11000011
            rawData[position + 1] = (byte) (sample & 0xff);
            // 10101010 11000011 -> ???????? 10101010 -> 10101010
            rawData[position] = (byte) ((sample >> 8) & 0xff);
        } else {
            rawData[position] = (byte) (sample & 0xff);
            rawData[position + 1] = (byte) ((sample >> 8) & 0xff);
        }
    }
}
