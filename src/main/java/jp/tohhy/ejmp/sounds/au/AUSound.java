package jp.tohhy.ejmp.sounds.au;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

import jp.tohhy.ejmp.interfaces.Media;

public class AUSound extends Media {
    private AudioClip clip;

    public AUSound(File file) {
        super(file);
        loadAudioClip(file);
    }

    public AUSound(String resourcePath) {
        super(resourcePath);
        loadAudioClip(resourcePath);
    }

    @Override
    public void reload() {
        setClip(null);
        if(getFile() != null && getFile().exists()) {
            loadAudioClip(getFile());
        } else if(getResourcePath() != null) {
            loadAudioClip(getResourcePath());
        }
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.AU;
    }

    @Override
    public void dispose() throws Exception {
        clip = null;
    }

    private void loadAudioClip(File file) {
        try {
            setClip(Applet.newAudioClip(file.toURI().toURL()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void loadAudioClip(String resourcePath) {
        setClip(Applet.newAudioClip(getClass().getClassLoader().getResource(resourcePath)));
    }

    public void setClip(AudioClip clip) {
        this.clip = clip;
    }

    public AudioClip getClip() {
        return clip;
    }

}
