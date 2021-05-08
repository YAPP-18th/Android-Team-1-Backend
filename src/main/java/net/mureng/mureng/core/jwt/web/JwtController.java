package net.mureng.mureng.core.jwt.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.core.jwt.component.JwtCreator;
import net.mureng.mureng.core.oauth2.dto.OAuth2Profile;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.repository.MemberRepository;
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
    private final JwtCreator jwtCreator;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "JWT 발급", notes = "JWT를 발급합니다.")
    @PostMapping()
    public ResponseEntity<ApiResult<String>> issue(
            @ApiParam(value = "사용자 Email", required = true) @RequestBody @Valid OAuth2Profile profile
            ) {

        Member member = memberRepository.findByEmail(profile.getEmail()).orElseThrow(() -> new BadRequestException("해당 유저 정보가 없습니다."));

        String jwt = jwtCreator.createToken(member.getEmail(), member.getNickname());

        return ResponseEntity.ok(ApiResult.ok(jwt));
    }


}
