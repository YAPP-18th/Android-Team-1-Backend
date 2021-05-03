package net.mureng.mureng.core.exception;

/**
 * 머렝 도메인 비즈니스 예외 (500 Internal Server Error)
 */
public class MurengException extends RuntimeException {
    public MurengException() {
    }

    public MurengException(String message) {
        super(message);
    }

    public MurengException(String message, Throwable cause) {
        super(message, cause);
    }

    public MurengException(Throwable cause) {
        super(cause);
    }

    public MurengException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
