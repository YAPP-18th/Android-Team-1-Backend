package net.mureng.core.core.exception;

/**
 * 리소스 접근 권한 없음 예외 (403 Forbidden)
 */
public class AccessNotAllowedException extends RuntimeException {
    public AccessNotAllowedException() {
    }

    public AccessNotAllowedException(String message) {
        super(message);
    }

    public AccessNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessNotAllowedException(Throwable cause) {
        super(cause);
    }

    public AccessNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
