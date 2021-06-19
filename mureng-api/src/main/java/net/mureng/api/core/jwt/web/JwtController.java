package net.mureng.api.core.jwt.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.core.jwt.dto.TokenDto;
import net.mureng.api.core.jwt.service.JwtService;
import net.mureng.api.core.oauth2.dto.OAuth2Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Api(value = "JWT 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class JwtController {
    private final JwtService jwtService;

    @Deprecated
    @ApiOperation(value = "JWT 발급(Deprecated)", notes = "JWT를 발급합니다.(Deprecated)")
    @PostMapping
    public ResponseEntity<ApiResult<DeprecatedTokenDto>> issue(
            @ApiParam(value = "사용자 id", required = true) @RequestBody @Valid OAuth2Profile profile
            ) {

        TokenDto.Mureng token = jwtService.issue(profile.getIdentifier());

        return ResponseEntity.ok(ApiResult.ok(
                new DeprecatedTokenDto(token.getMurengAccessToken(), token.getMurengRefreshToken())
        ));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class DeprecatedTokenDto {
        private String accessToken;
        private String refreshToken;
    }
}
