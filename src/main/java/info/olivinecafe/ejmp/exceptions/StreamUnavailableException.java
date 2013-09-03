package info.olivinecafe.ejmp.exceptions;

import java.io.IOException;

/**
 * メディアからのストリームの読み込みに失敗した場合にスローされる.
 * @author tohhy
 */
public class StreamUnavailableException extends IOException {
    private static final long serialVersionUID = 8955031100963834825L;

    public StreamUnavailableException() {
        super();
    }

    public StreamUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public StreamUnavailableException(String message) {
        super(message);
    }

    public StreamUnavailableException(Throwable cause) {
        super(cause);
    }
}
