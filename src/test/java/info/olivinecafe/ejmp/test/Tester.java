package info.olivinecafe.ejmp.test;

import info.olivinecafe.ejmp.media.MediaLocation;
import info.olivinecafe.ejmp.sounds.SoundPlayer;
import info.olivinecafe.ejmp.sounds.filters.DelayFilter;
import info.olivinecafe.ejmp.sounds.midi.MIDIPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Tester {
    private final SoundPlayer player = new SoundPlayer();
    private final DelayFilter delayFilter = new DelayFilter((int)Math.pow(2, 15), 0.0);
    
    private String baseFileName = "testresources/test.";
    
    public Tester() {
        MediaLocation media = getTestMedia("aiff");
        player.setMedia(media);
        player.getFilters().add(delayFilter);
        Box wrapper = new Box(BoxLayout.Y_AXIS);
        createSoundPlayerAddons(player, wrapper);
        createSoundComponents(wrapper);
        new TestWindow(player, wrapper);
    }
    

    private void createSoundPlayerAddons(final SoundPlayer player, final Box wrapper) {
        Box buttonsB = new Box(BoxLayout.X_AXIS);
        buttonsB.add(new JButton(new AbstractAction("fadeIn") {
            public void actionPerformed(ActionEvent e) {
                player.fadeIn(5000);
            }
        }));
        buttonsB.add(new JButton(new AbstractAction("fadeOut") {
            public void actionPerformed(ActionEvent e) {
                player.fadeOut(5000);
            }
        }));
        buttonsB.add(new JButton(new AbstractAction("stopFade") {
            public void actionPerformed(ActionEvent e) {
                player.stopFade();
            }
        }));
        wrapper.add(buttonsB);
    }
    
    private void createSoundComponents(final Box wrapper) {

        final JSlider delay = new JSlider(0, 100, (int)(delayFilter.getFeedBackGain()*100));
        delay.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                delayFilter.setFeedBackGain((double) delay.getValue() / 100);
            }
        });
        
        wrapper.add(delay);
    }
    
    private MediaLocation getTestMedia(String ext) {
        return new MediaLocation(new File(baseFileName + ext));
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
