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
    
    /**
     * フィードバックゲインを指定する.
     * 過去のサウンドがこの倍率をかけられて現在のサウンドに加算される.
     * ゲインは0.0~1.0まで許容され、範囲外の値は丸められる.
     * 音割れが生じないようにゲインの大きさに応じてディレイ加算後の音圧に自動で補正がかかる.
     * 補正をかけない場合RawDelayFilterを使う.
     * @param feedBackGain フィードバックゲイン
     */
    @Override
    public void setFeedBackGain(double a) {
        //極端なaの値を修正
        if(a > 1.0) a = 1.0;
        if(a <= 0) {a = 0; b = 1; super.setFeedBackGain(a); return;}
        super.setFeedBackGain(a);
        //音割れを防ぐ(元の音圧より増幅されないようにする)には b(ab+1)<1
        b = ((Math.sqrt(4 * a + 1) - 1) / (2 * a));
    }

}
