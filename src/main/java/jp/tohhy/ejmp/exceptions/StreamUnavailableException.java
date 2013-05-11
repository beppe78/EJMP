package jp.tohhy.ejmp.exceptions;

import java.io.IOException;

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
