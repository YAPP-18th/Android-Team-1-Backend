package net.mureng.mureng.core.jwt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
