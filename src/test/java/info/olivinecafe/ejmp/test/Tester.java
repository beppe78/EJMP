package info.olivinecafe.ejmp.test;

import info.olivinecafe.ejmp.media.MediaPlayer;
import info.olivinecafe.ejmp.sounds.SoundPlayer;
import info.olivinecafe.ejmp.sounds.filters.DelayFilter;
import info.olivinecafe.ejmp.sounds.filters.SoundFilter;
import info.olivinecafe.ejmp.sounds.midi.MIDIPlayer;
import info.olivinecafe.ejmp.utils.MediaLocation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Tester {
    private final SoundPlayer player = new SoundPlayer();
    private final DelayFilter delayFilter = new DelayFilter((int)Math.pow(2, 15), 0.0);
    
    public Tester() {
        player.getFilters().add(delayFilter);
        player.setMedia(new MediaLocation(new File("testresources/test.wav")));
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 400, 200);
        Box wrapper = new Box(BoxLayout.Y_AXIS);
        createComponents(player, wrapper);
        createSoundPlayerAddons(player, wrapper);
        frame.add(wrapper);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void createSoundPlayerAddons(final SoundPlayer player, final Box wrapper) {
        Box buttonsB = new Box(BoxLayout.X_AXIS);
        buttonsB.add(new JButton(new AbstractAction("fadeIn") {
            public void actionPerformed(ActionEvent e) {
                player.fadeIn(500);
            }
        }));
        buttonsB.add(new JButton(new AbstractAction("fadeOut") {
            public void actionPerformed(ActionEvent e) {
                player.fade(0.0, 500);
            }
        }));
        buttonsB.add(new JButton(new AbstractAction("stopFade") {
            public void actionPerformed(ActionEvent e) {
                player.stopFade();
            }
        }));

        wrapper.add(buttonsB);
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
                player.setVolume((double)volume.getValue()/100);
            }
        });
        final JSlider pan = new JSlider(-100, 100, 0);
        pan.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                player.setPan((double)pan.getValue()/100);
            }
        });
        final JSlider delay = new JSlider(0, 100, (int)(delayFilter.getFeedBackGain()*100));
        delay.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                delayFilter.setFeedBackGain((double) delay.getValue() / 100);
            }
        });
        wrapper.add(buttonsA);
        wrapper.add(isLoop);
        wrapper.add(volume);
        wrapper.add(pan);
        wrapper.add(delay);
    }
    
    private static void createMidiAddons(final MIDIPlayer player, final Box wrapper) {
        final int resolution = 1000;
        final JSlider tick = new JSlider(0, resolution, 0);
        tick.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                player.setTick((player.getTickLength() * tick.getValue())/resolution);
            }
        });
        final JComboBox soundfonts = new JComboBox();
        ArrayList<File> sflist = new ArrayList<File>();
        //指定ディレクトリにあるサウンドフォントを全て読み込む
        for(File f : new File("soundfonts").listFiles()) {
            if(f.getName().endsWith("sf2")) {
                sflist.add(f);
            }
        }
        soundfonts.setModel(new DefaultComboBoxModel(sflist.toArray()));
        soundfonts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(player.isPlaying()) {player.stop();}
                player.setSoundFont((File)soundfonts.getSelectedItem());
            }
        });
        wrapper.add(tick);
        wrapper.add(soundfonts);
    }

    public static void main(String[] args) {
        new Tester();
    }

}
