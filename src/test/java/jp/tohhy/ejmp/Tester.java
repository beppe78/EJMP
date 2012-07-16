package jp.tohhy.ejmp;

import jp.tohhy.ejmp.sounds.SoundPlayer;

import org.apache.commons.io.FilenameUtils;

public class Tester {

    public Tester() {
        System.out.println(FilenameUtils.getExtension("resources/xxx/test.mp2"));
        SoundPlayer player = new SoundPlayer();
        player.setMedia("resources/sound.mid");
        player.setRepeat(true);
        player.play();
    }

    public static void main(String[] args) {
        new Tester();
    }

}
