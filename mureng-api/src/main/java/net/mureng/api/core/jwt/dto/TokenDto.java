package net.mureng.api.core.jwt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="Token Dto", description="JWT 및 Refresh 토큰 모델")
public class TokenDto {
    @ApiModelProperty(value = "Access Token")
    private String accessToken;

    @ApiModelProperty(value = "Refresh Token")
    private String refreshToken;
}
