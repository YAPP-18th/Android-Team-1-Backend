package net.mureng.mureng.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.oauth2.dto.OAuth2Profile;
import net.mureng.mureng.core.oauth2.service.OAuth2Service;
import net.mureng.mureng.member.component.MemberMapper;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value = "회원 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final OAuth2Service oAuth2Service;

    @ApiOperation(value = "신규 회원 가입", notes = "신규 회원가입입니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResult<MemberDto>> signup(
            @ApiParam(value = "신규 회원 정보", required = true)
            @RequestBody @Valid MemberDto memberDto) {

        Member newMember = memberMapper.map(memberDto);
        return ResponseEntity.ok(ApiResult.ok(memberMapper.map(
            memberService.signup(newMember)
        )));
    }

    @ApiOperation(value = "중복 닉네임 체크", notes = "사용하려는 닉네임이 중복인지 체크합니다.")
    @GetMapping("/nickname-exists/{nickname}")
    public ResponseEntity<ApiResult<DuplicatedCheckDto>> nicknameExists(
            @ApiParam(value = "신규 회원 정보", required = true)
            @PathVariable String nickname) {

        return ResponseEntity.ok(ApiResult.ok(
                new DuplicatedCheckDto(memberService.isNicknameDuplicated(nickname))
        ));
    }

    @ApiOperation(value = "사용자 존재 체크", notes = "기존에 있던 사용자인지 체크합니다.")
    @PostMapping("/user-exists/{provider}")
    public ResponseEntity<ApiResult<ExistCheckDto>> userExists(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "google") @PathVariable String provider,
            @ApiParam(value = "액세스 토큰", required = true) @RequestBody String accessToken) {

        OAuth2Profile profile = oAuth2Service.getProfile(provider, accessToken);
        return ResponseEntity.ok(ApiResult.ok(
                new ExistCheckDto(memberService.isEmailExist(profile.getEmail())), profile.getEmail()
        ));
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class DuplicatedCheckDto {
        private final boolean duplicated;
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class ExistCheckDto {
        private final boolean exist;
    }
}
