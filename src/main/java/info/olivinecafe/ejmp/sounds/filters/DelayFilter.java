package info.olivinecafe.ejmp.sounds.filters;

public class DelayFilter extends RawDelayFilter {

    /**
     * サウンドにディレイをかけるフィルタ.
     * @param delayLength ディレイの長さ（サンプル数）
     * @param feedBackGain フィードバックの強さ（0.0~1.0）
     */
    public DelayFilter(int delayLength, double feedBackGain) {
        super(delayLength, feedBackGain);
    }
    
    @Override
    public void setFeedBackGain(double a) {
        //極端なaの値を修正
        if(a > 1.0) a = 1.0;
        if(a < 0) a = 0;
        super.setFeedBackGain(a);
        //音割れを防ぐ(元の音圧より増幅されないようにする)には b(ab+1)<1
        b = ((Math.sqrt(4 * a + 1) - 1) / (2 * a));
    }

}
