package net.mureng.mureng.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResult<T> {
    private final String message;
    private final T data;

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>("ok", data);
    }
}
