package net.mureng.core.core.exception;

import net.mureng.core.core.exception.MurengException;
import net.mureng.core.core.exception.ResourceNotFoundException;

public class EntityNotFoundException extends ResourceNotFoundException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
