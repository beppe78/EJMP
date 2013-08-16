package jp.tohhy.ejmp.utils;

public class FileUtils {

    public static String getExtension(String fileName) {
        if (fileName != null) {
            int dotindex = fileName.lastIndexOf(".");
            if (dotindex == -1) {
                return fileName;
            } else {
                return fileName.substring(dotindex + 1);
            }
        }
        return null;
    }

}
