package jp.tohhy.ejmp.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StreamUtils {

    /**
     * ファイルを読み出すBufferedInputStreamを適切に生成して返す.
     * @param file 読み出すファイル
     * @return リソースを読み出すBufferedInputStream
     * @throws FileNotFoundException
     */
    public static BufferedInputStream getFileAsStream(File file) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    /**
     * リソースを読み出すBufferedInputStreamを適切に生成して返す.
     * @param resourcePath 読み出すリソースのパス
     * @return リソースを読み出すBufferedInputStream
     */
    public static BufferedInputStream getResourceAsStream(String resourcePath) {
        return new BufferedInputStream(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath));
    }

}
