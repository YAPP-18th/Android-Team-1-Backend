package net.mureng.core.core.exception.business;

public class ReplyLikesException extends MurengException{
    public ReplyLikesException() {
    }

    public ReplyLikesException(String message) {
        super(message);
    }

    public ReplyLikesException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplyLikesException(Throwable cause) {
        super(cause);
    }

    public ReplyLikesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
