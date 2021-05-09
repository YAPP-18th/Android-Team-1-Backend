package net.mureng.mureng.core.jwt.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.jwt.dto.TokenDto;
import net.mureng.mureng.core.jwt.service.JwtService;
import net.mureng.mureng.core.oauth2.dto.OAuth2Profile;
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

    @ApiOperation(value = "JWT 발급", notes = "JWT를 발급합니다.")
    @PostMapping
    public ResponseEntity<ApiResult<TokenDto>> issue(
            @ApiParam(value = "사용자 Email", required = true) @RequestBody @Valid OAuth2Profile profile
            ) {

        TokenDto token = jwtService.issue(profile.getEmail());

        return ResponseEntity.ok(ApiResult.ok(token));
    }


}
