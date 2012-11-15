package jp.tohhy.ejmp.sounds.au;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

import jp.tohhy.ejmp.interfaces.AbstractMedia;

public class AUSound extends AbstractMedia {
    private AudioClip clip;

    public AUSound(File file) {
        super(file);
        loadAudioClip(getUrl());
    }

    public AUSound(String resourcePath) {
        super(resourcePath);
        loadAudioClip(getUrl());
    }

    public void reload() {
        setClip(null);
        loadAudioClip(getUrl());
    }

    
    private void loadAudioClip(URL url) {
        setClip(Applet.newAudioClip(url));
    }

    public void setClip(AudioClip clip) {
        this.clip = clip;
    }

    public AudioClip getClip() {
        return clip;
    }

    public MediaType getMediaType() {
        return MediaType.AU;
    }

    public void dispose() throws Exception {
        clip = null;
    }

}
