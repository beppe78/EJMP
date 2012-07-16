package jp.tohhy.ejmp;

import java.awt.event.ActionEvent;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import jp.tohhy.ejmp.sounds.SoundPlayer;
import jp.tohhy.ejmp.sounds.wave.WavePlayer;

public class Tester {

    public Tester() {
//        final SoundPlayer player = new SoundPlayer();
        final WavePlayer player = new WavePlayer();
        player.setMedia("resources/sound.wav");
        player.setRepeat(false);

        Timer timer = new Timer();

        JFrame frame = new JFrame();
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(new JButton(new AbstractAction("play") {
            public void actionPerformed(ActionEvent e) {
                player.play();
            }
        }));
        box.add(new JButton(new AbstractAction("stop") {
            public void actionPerformed(ActionEvent e) {
                player.stop();
            }
        }));
        box.add(new JButton(new AbstractAction("restart") {
            public void actionPerformed(ActionEvent e) {
                player.restart();
            }
        }));
        frame.setBounds(100,100,300,200);
        frame.add(box);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        player.play();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                player.restart();
//                System.out.println("played");
//            }
//        }, 0, 5000);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                player.stop();
//                System.out.println("stopped");
//            }
//        }, 2500, 5000);
    }

    public static void main(String[] args) {
        new Tester();
    }

}
