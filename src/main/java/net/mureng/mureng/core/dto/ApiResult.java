package net.mureng.mureng.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@ApiModel(value="API 응답 공통 모델")
public class ApiResult<T> {

    @ApiModelProperty(value = "결과 메세지")
    protected final String message;

    @ApiModelProperty(value = "응답 데이터, 실패시 null")
    protected final T data;

    @ApiModelProperty(value = "요청 서버 시간")
    protected final long timestamp;

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
