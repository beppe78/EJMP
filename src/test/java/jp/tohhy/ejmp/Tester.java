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
        player.setMedia("resources/test.ogg");

        JFrame frame = new JFrame();
        Box buttons = new Box(BoxLayout.X_AXIS);
        buttons.add(new JButton(new AbstractAction("play") {
            public void actionPerformed(ActionEvent e) {
                player.play();
            }
        }));
        buttons.add(new JButton(new AbstractAction("stop") {
            public void actionPerformed(ActionEvent e) {
                player.stop();
            }
        }));
        buttons.add(new JButton(new AbstractAction("restart") {
            public void actionPerformed(ActionEvent e) {
                player.restart();
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
        frame.setBounds(100,100,300,200);
        Box wrapper = new Box(BoxLayout.Y_AXIS);
        wrapper.add(buttons);
        wrapper.add(isLoop);
        wrapper.add(volume);
        frame.add(wrapper);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Tester();
    }

}
