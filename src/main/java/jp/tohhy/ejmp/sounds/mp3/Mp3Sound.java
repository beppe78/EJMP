package jp.tohhy.ejmp.sounds.mp3;

import java.io.File;
import java.net.URI;
import java.net.URL;

import jp.tohhy.ejmp.sounds.spi.SpiSound;


public class Mp3Sound extends SpiSound {

    public Mp3Sound(File file) {
        super(file);
    }

    public Mp3Sound(String resourcePath) {
        super(resourcePath);
    }

    public Mp3Sound(URI uri) {
        super(uri);
    }

    public Mp3Sound(URL url) {
        super(url);
    }

    public MediaType getMediaType() {
        return MediaType.MP3;
    }
    
    
    
//    private BufferedInputStream stream;
//
//    public Mp3Sound(File file) {
//        super(file);
//        try {
//            loadStream(getUrl());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Mp3Sound(String resourcePath) {
//        super(resourcePath);
//        try {
//            loadStream(getUrl());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public MediaType getMediaType() {
//        return MediaType.MP3;
//    }
//
//    public void setStream(BufferedInputStream stream) {
//        this.stream = stream;
//        stream.mark(0);
//    }
//
//    public BufferedInputStream getStream() {
//        return stream;
//    }
//
//    public void dispose() throws Exception {
//        if(stream != null)
//            stream.close();
//    }
//
//    public void reload() {
//        try {
//            if(stream != null)
//                stream.close();
//            loadStream(getUrl());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    
//    private void loadStream(URL url) throws IOException {
//        this.setStream(StreamUtils.getURLAsStream(url));
//    }
}
