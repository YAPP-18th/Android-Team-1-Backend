package net.mureng.api.core.jwt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class TokenDto {
    private TokenDto() { }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ApiModel(value="Provider Token Dto", description="Provider 토큰 모델")
    public static class Provider {
        @ApiModelProperty(value = "Provider 이름 (Kakao / Google)")
        private TokenProvider providerName;

        @ApiModelProperty(value = "Provider 제공 Access Token")
        private String providerAccessToken;

        public void setProviderName(String providerName) {
            this.providerName = TokenProvider.create(providerName);
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ApiModel(value="Mureng Token Dto", description="Mureng 제공 JWT 및 Refresh 토큰 모델")
    public static class Mureng {
        @ApiModelProperty(value = "Mureng 제공 Access Token (jwt)")
        private String murengAccessToken;

        @ApiModelProperty(value = "Mureng 제공 Refresh Token (jwt)")
        private String murengRefreshToken;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ApiModel(value="Mureng Refresh Token Dto", description="Mureng 제공 JWT 및 Refresh 토큰 모델")
    public static class MurengRefresh {
        @ApiModelProperty(value = "Mureng 제공 Refresh Token (jwt)")
        private String murengRefreshToken;
    }
}
