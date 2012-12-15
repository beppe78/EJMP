package jp.tohhy.ejmp;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jp.tohhy.ejmp.sounds.SoundPlayer;

public class Tester {

    public Tester() {
        final SoundPlayer player = new SoundPlayer();
        player.setMedia("resources/test.aiff");

        JFrame frame = new JFrame();
        Box buttonsA = new Box(BoxLayout.X_AXIS);
        buttonsA.add(new JButton(new AbstractAction("play") {
            public void actionPerformed(ActionEvent e) {
                player.play();
            }
        }));
        buttonsA.add(new JButton(new AbstractAction("stop") {
            public void actionPerformed(ActionEvent e) {
                player.stop();
            }
        }));
        buttonsA.add(new JButton(new AbstractAction("restart") {
            public void actionPerformed(ActionEvent e) {
                player.restart();
            }
        }));
        Box buttonsB = new Box(BoxLayout.X_AXIS);
        buttonsB.add(new JButton(new AbstractAction("fadeIn") {
            public void actionPerformed(ActionEvent e) {
                player.fadeIn(2000);
            }
        }));
        buttonsB.add(new JButton(new AbstractAction("fadeOut") {
            public void actionPerformed(ActionEvent e) {
                player.fadeOut(2000);
            }
        }));
        buttonsB.add(new JButton(new AbstractAction("rewind") {
            public void actionPerformed(ActionEvent e) {
                player.rewind();
            }
        }));
        
        JCheckBox isLoop = new JCheckBox(new AbstractAction("isLoop") {
            public void actionPerformed(ActionEvent e) {
                player.setLoop(((JCheckBox)e.getSource()).isSelected());
            }
        });
        final JSlider volume = new JSlider(0, 200, 100);
        volume.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                player.setVolume((double)volume.getValue()/100);
            }
        });
        final JSlider pan = new JSlider(-100, 100, 0);
        pan.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                player.setPan((double)pan.getValue()/100);
            }
        });
        frame.setBounds(100,100,300,200);
        Box wrapper = new Box(BoxLayout.Y_AXIS);
        wrapper.add(buttonsA);
        wrapper.add(buttonsB);
        wrapper.add(isLoop);
        wrapper.add(volume);
        wrapper.add(pan);
        frame.add(wrapper);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Tester();
    }

}
