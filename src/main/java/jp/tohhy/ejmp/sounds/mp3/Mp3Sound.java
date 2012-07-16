package jp.tohhy.ejmp.sounds.mp3;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;

import jp.tohhy.ejmp.interfaces.Media;
import jp.tohhy.ejmp.utils.StreamUtils;

public class Mp3Sound extends Media {
    private BufferedInputStream stream;

    public Mp3Sound(File file) {
        super(file);
        try {
            loadStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Mp3Sound(String resourcePath) {
        super(resourcePath);
        loadStream(getResourcePath());
    }

    public void streamReload() {
        if(getFile() != null) {
            try {
                loadStream(getFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            loadStream(getResourcePath());
        }
    }

    private void loadStream(File file) throws FileNotFoundException {
        this.setStream(StreamUtils.getFileAsStream(file));
    }

    private void loadStream(String resourcePath) {
        this.setStream(StreamUtils.getResourceAsStream(resourcePath));
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.MP3;
    }

    public void setStream(BufferedInputStream stream) {
        this.stream = stream;
        stream.mark(0);
    }

    public BufferedInputStream getStream() {
        return stream;
    }

    public void dispose() throws Exception {
        if (stream != null)
            stream.close();
    }

}
