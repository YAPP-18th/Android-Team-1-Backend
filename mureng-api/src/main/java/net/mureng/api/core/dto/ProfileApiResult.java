package net.mureng.api.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(value="프로필 API 응답 공통 모델")
public class ProfileApiResult<T> extends ApiResult<T>{
    @ApiModelProperty(value = "요청자 프로필 여부", position = 3)
    private final boolean requesterProfile;

    public ProfileApiResult(T data, String message,  boolean requesterProfile) {
        super(message, data);
        this.requesterProfile = requesterProfile;
    }

    public static <T> ProfileApiResult<T> ok(T data, boolean requesterProfile) {
        return ok(data, "ok", requesterProfile);
    }

    public static <T> ProfileApiResult<T> ok(T data, String message, boolean requesterProfile) {
        return new ProfileApiResult<>(data, message, requesterProfile);
    }

}
