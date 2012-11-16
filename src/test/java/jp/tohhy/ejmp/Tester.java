package jp.tohhy.ejmp;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import jp.tohhy.ejmp.sounds.SoundPlayer;

public class Tester {
 

    public Tester() {
        final SoundPlayer player = new SoundPlayer();
        player.setMedia("resources/test.mp3");

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
        box.add(new JCheckBox(new AbstractAction("isLoop") {
            public void actionPerformed(ActionEvent e) {
                player.setLoop(((JCheckBox)e.getSource()).isSelected());
            }
        }));
        frame.setBounds(100,100,300,200);
        frame.add(box);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Tester();
    }

}
