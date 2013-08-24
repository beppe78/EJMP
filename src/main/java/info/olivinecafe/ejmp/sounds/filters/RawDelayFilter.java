package info.olivinecafe.ejmp.sounds.filters;

import javax.sound.sampled.AudioFormat;

/**
 * 音割れの防止機構を含まない生のディレイフィルタ.
 * @author tohhy
 */
public class RawDelayFilter extends SoundFilter {
    /**
     * フィードバックゲイン.
     */
    private double a;
    
    /**
     * 音割れ防止のためにフィルタ後のサンプルの音圧にかける補正.
     * DelayFilterではsetFeedBackGainを呼び出した際に自動的に適切な値に設定される.
     */
    protected double b = 1.0;
    
    /**
     * ディレイの長さ（サンプル数）
     */
    private final int delayLength;
    
    /**
     * ディレイログを格納する配列
     */
    private final short[] delayBuffer;
    
    /**
     * ディレイログの現在の参照位置
     */
    private int delayPos = 0;
    
    /**
     * サウンドにディレイをかけるフィルタから音割れ防止の補正を除いたもの.
     * @param delayLength ディレイの長さ（サンプル数）
     * @param feedBackGain フィードバックの強さ
     */
    public RawDelayFilter(int delayLength, double feedBackGain) {
        this.delayLength = delayLength;
        this.delayBuffer = new short[delayLength];
        setFeedBackGain(feedBackGain);
    }
    
    @Override
    public void filter(byte[] stream, int offset, int length, AudioFormat format) {
        for(int i=offset; i<offset + length; i+=2) {
            short oldSample = getSample(stream, i, format.isBigEndian());
            int filtered = (int) (oldSample + (a * delayBuffer[delayPos]));
            short newSample = (short)(filtered * b);
            setSample(stream, i, newSample, format.isBigEndian());
            delayBuffer[delayPos] = newSample;
            delayPos++;
            if(delayPos == delayLength)
                delayPos = 0;
        }
    }

    /**
     * フィードバックゲインを取得する.
     * 過去のサウンドがこの倍率をかけられて現在のサウンドに加算される.
     * @return フィードバックゲイン
     */
    public double getFeedBackGain() {
        return a;
    }

    /**
     * フィードバックゲインを指定する.
     * 過去のサウンドがこの倍率をかけられて現在のサウンドに加算される.
     * @param feedBackGain フィードバックゲイン
     */
    public void setFeedBackGain(double feedBackGain) {
        this.a = feedBackGain;
    }
}
