package jp.tohhy.ejmp.utils;

public class PlayThread extends Thread {
    private boolean isEnd = false;
    private final Playable p;

    public PlayThread(Playable p) {
        this.p = p;
    }

    @Override
    public void run() {
        p.play(this);
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public interface Playable {
        /**
         * メディアの再生処理を記述する.runから呼び出される.
         * PlayThreadのisEndがtrueになったら即座にループを抜けるように実装する.
         */
        public void play(PlayThread thread);
    }
}
