package info.olivinecafe.ejmp.sounds;

import java.util.Timer;
import java.util.TimerTask;

public class Fade {
    private final SoundPlayer player;
    private final double toVolume;
    private final int time;
    private final int taskPeriod = 100;
    private final Timer timer = new Timer();
    private Runnable endTask = new Runnable() {
        public void run() {
            player.setVolume(toVolume);
        }
    };
    
    protected Fade(SoundPlayer player, double toVolume, int time, Runnable endTask) {
        this.player = player;
        this.toVolume = toVolume;
        this.time = time;
        if(endTask != null) 
            this.endTask = endTask;
        startTimer();
    }
    
    protected Fade(SoundPlayer player, double toVolume, int time) {
        this(player, toVolume, time, null);
    }
    
    private void startTimer() {
        final double defaultVolume = player.getVolume();
        final int taskCount = (time/taskPeriod);
        final double dVolume = (toVolume - defaultVolume)/taskCount;

        //フェード開始、初期ボリュームをプレイヤーのボリュームに合わせる
        player.setFading(true);
        player.setFadeVolume(player.getVolume());
        //タスクをタイマーに追加
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!player.isFading()) {
                    timer.cancel();
                    return;
                }
                player.setFadeVolume((player.getFadeVolume() + dVolume));
                if(dVolume > 0) {
                    if(player.getFadeVolume() >= toVolume) {
                        endTask.run();
                        player.setFading(false);
                        timer.cancel();
                    }
                } else {
                    if(player.getFadeVolume() <= toVolume) {
                        endTask.run();
                        player.setFading(false);
                        timer.cancel();
                    }
                }
            }
        }, taskPeriod, taskPeriod);
    }
}
