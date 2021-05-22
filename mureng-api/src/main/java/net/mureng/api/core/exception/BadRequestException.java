package net.mureng.api.core.exception;

import net.mureng.core.core.exception.MurengException;

/**
 * 리소스 잘못된 요청 예외 (400 Bad Request)
 */
public class BadRequestException extends MurengException {
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
