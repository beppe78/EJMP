package info.olivinecafe.ejmp.sounds.filters;

public abstract class SoundFilter {
    
    /**
     * サウンドを表すバイトデータをフィルタリングする.
     * @param rawData サウンドを表すバイトデータの配列
     * @param offset 開始オフセット、省略すると0
     * @param length 配列の長さ、省略するとstream.length
     */
    public abstract void filter(byte[] rawData, int offset, int length);
    
    /**
     * サウンドを表すバイトデータをフィルタリングする.
     * @param rawData サウンドを表すバイトデータの配列
     */
    public void filter(byte[] rawData) {
        filter(rawData, 0, rawData.length);
    }
    
    /**
     * Convenience method for getting a 16-bit sample from a byte array.
     * Samples should be in 16-bit, signed, little-endian format.
     */
    public static short getSample(byte[] rawData, int position) {
      return (short)(((rawData[position + 1] & 0xff) << 8) | (rawData[position] & 0xff));
    }
    
    /**
     * Convenience method for setting a 16-bit sample in a byte array.
     * Samples should be in 16-bit, signed, little-endian format.
     */
    public static void setSample(byte[] rawData, int position, short sample) {
      // 0xff => 11111111
      // 10101010 11000011 -> 11000011
      rawData[position] = (byte) (sample & 0xff);
      // 10101010 11000011 -> ???????? 10101010 -> 10101010
      rawData[position + 1] = (byte) ((sample >> 8) & 0xff);
    }
    
    public short toShort(double d) {
        return toShort((int)d);
    }
    
    public short toShort(int i) {
        short s = (short) i;
        return s;
    }
}
