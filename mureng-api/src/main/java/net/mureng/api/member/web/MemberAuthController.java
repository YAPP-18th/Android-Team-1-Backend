package net.mureng.api.member.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.*;
import lombok.*;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.core.jwt.dto.TokenDto;
import net.mureng.api.core.jwt.service.JwtService;
import net.mureng.api.core.oauth2.dto.OAuth2Profile;
import net.mureng.api.core.oauth2.service.OAuth2Service;
import net.mureng.api.member.component.MemberMapper;
import net.mureng.api.member.dto.MemberDto;
import net.mureng.api.member.service.MemberSignupService;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value = "회원 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberAuthController {
    private final MemberService memberService;
    private final OAuth2Service oAuth2Service;
    private final JwtService jwtService;

    @Deprecated
    @ApiOperation(value = "사용자 존재 체크(Deprecated)", notes = "기존에 있던 사용자인지 체크합니다.(Deprecated)")
    @PostMapping("/user-exists/{provider}")
    public ResponseEntity<ApiResult<UserCheckDto>> userExists(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "액세스 토큰", required = true) @RequestBody @Valid TokenDto.Provider providerToken) {
        providerToken.setProviderName(provider);
        return userExists(providerToken);
    }

    @ApiOperation(value = "사용자 존재 체크", notes = "기존에 있던 사용자인지 체크합니다.")
    @PostMapping("/user-exists")
    public ResponseEntity<ApiResult<UserCheckDto>> userExists(
            @ApiParam(value = "액세스 토큰", required = true) @RequestBody @Valid TokenDto.Provider providerToken) {
        OAuth2Profile profile = oAuth2Service.getProfile(providerToken);
        return ResponseEntity.ok(ApiResult.ok(
                new UserCheckDto(memberService.isMemberExist(profile.getIdentifier()), profile.getIdentifier())
        ));
    }

    @ApiOperation(value = "사용자 로그인 (JWT 발급)", notes = "사용자에게 JWT를 발급합니다.")
    @PostMapping("/signin")
    public ResponseEntity<ApiResult<TokenDto.Mureng>> signin(
            @ApiParam(value = "액세스 토큰", required = true) @RequestBody @Valid TokenDto.Provider providerToken) {
        return ResponseEntity.ok(ApiResult.ok(
                jwtService.issue(providerToken)));
    }

    @ApiOperation(value = "JWT 발급 새로 고침", notes = "refreshToken을 사용하여 JWT를 새로고침 합니다")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResult<TokenDto.Mureng>> refresh(
            @ApiParam(value = "액세스 토큰", required = true) @RequestBody @Valid TokenDto.MurengRefresh murengRefreshToken) {
        return ResponseEntity.ok(ApiResult.ok(
                jwtService.refresh(murengRefreshToken)));
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class UserCheckDto {
        private final boolean exist;
        private final String identifier;
    }
}
