package jp.tohhy.ejmp;

import java.awt.event.ActionEvent;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

import jp.tohhy.ejmp.sounds.SoundPlayer;

public class Tester {

    public Tester() {
        final SoundPlayer player = new SoundPlayer();
        player.setMedia("resources/sound.mp3");
        player.setRepeat(true);
        player.play();

        Timer timer = new Timer();

        JFrame frame = new JFrame();
        frame.add(new JButton(new AbstractAction("restart") {
            public void actionPerformed(ActionEvent e) {
                player.restart();
                System.out.println("played!!");
            }
        }));
        frame.setBounds(100,100,300,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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
