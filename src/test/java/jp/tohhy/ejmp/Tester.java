package jp.tohhy.ejmp;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import jp.tohhy.ejmp.sounds.wave.WavePlayer;

public class Tester {

    public Tester() {
//        final SoundPlayer player = new SoundPlayer();
        final WavePlayer player = new WavePlayer();
        player.setMedia("resources/test.wav");
//        player.setMedia("resources/test.mp3");
        player.setRepeat(false);
        
        File file = new File("src/test/java/resources/test.wav");

        try {
            Player jmf = Manager.createRealizedPlayer(file.toURI().toURL());
            jmf.start();
        } catch (NoPlayerException e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
        } catch (CannotRealizeException e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
        }
        
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
    }

    public static void main(String[] args) {
        new Tester();
    }

}
