package info.olivinecafe.ejmp.test;

import info.olivinecafe.ejmp.media.MediaPlayer;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class TestWindow extends JFrame {
    
    public TestWindow(MediaPlayer player, JComponent...components) {
        this.setBounds(100, 100, 400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Box wrapper = new Box(BoxLayout.Y_AXIS);
        createComponents(player, wrapper);
        for(JComponent c : components) {
            wrapper.add(c);
        }
        this.add(wrapper);
        this.setVisible(true);
    }
    
    private void createComponents(final MediaPlayer player, final Box wrapper) {
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

        buttonsA.add(new JButton(new AbstractAction("rewind") {
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
                player.setVolume((double)volume.getValue() / 100);
            }
        });
        final JSlider pan = new JSlider(-100, 100, 0);
        pan.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                player.setPan((double)pan.getValue() / 100);
            }
        });
        wrapper.add(buttonsA);
        wrapper.add(isLoop);
        wrapper.add(volume);
        wrapper.add(pan);
    }
}
