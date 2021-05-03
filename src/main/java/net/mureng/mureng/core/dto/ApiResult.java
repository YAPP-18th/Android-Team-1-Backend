package net.mureng.mureng.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResult<T> {
    private final String message;
    private final T data;
    private final long timestamp;

    public ApiResult(String message, T data) {
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ApiResult<T> ok(T data) {
        return ok(data, "ok");
    }

    public static <T> ApiResult<T> ok(T data, String message) {
        return new ApiResult<>(message, data);
    }

    public static ApiResult<?> fail(String message) {
        return new ApiResult<>(message, null);
    }
}
