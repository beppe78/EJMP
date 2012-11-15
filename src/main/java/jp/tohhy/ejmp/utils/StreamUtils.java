package jp.tohhy.ejmp.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class StreamUtils {
    
    /**
     * URLを読み出すBufferedInputStreamを適切に生成して返す.
     * @param resourcePath 読み出すリソースのパス
     * @return リソースを読み出すBufferedInputStream
     * @throws IOException 
     */
    public static BufferedInputStream getURLAsStream(URL url) throws IOException {
        return new BufferedInputStream(url.openStream());
    }

}
