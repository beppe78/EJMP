package info.olivinecafe.ejmp.sounds.filters;

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
    
    private int delayPos = 0;
    private final int delayLength;
    private final short[] delayBuffer;
    
    public RawDelayFilter(int delayLength, double feedBackGain) {
        this.delayLength = delayLength;
        this.delayBuffer = new short[delayLength];
        setFeedBackGain(feedBackGain);
    }
    
    @Override
    public void filter(byte[] stream, int offset, int length) {
        for(int i=offset; i<offset + length; i+=2) {
            short oldSample = getSample(stream, i);
            int filtered = (int) (oldSample + (a * delayBuffer[delayPos]));
            short newSample = (short)(filtered * b);
            setSample(stream, i, newSample);
            delayBuffer[delayPos] = newSample;
            delayPos++;
            if(delayPos == delayLength)
                delayPos = 0;
        }
    }

    public double getFeedBackGain() {
        return a;
    }

    public void setFeedBackGain(double a) {
        this.a = a;
    }
}
