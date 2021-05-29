package net.mureng.api.member.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.core.jwt.dto.TokenDto;
import net.mureng.api.core.oauth2.dto.OAuth2Profile;
import net.mureng.api.core.oauth2.service.OAuth2Service;
import net.mureng.api.member.component.MemberMapper;
import net.mureng.api.member.dto.MemberDto;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.service.MemberService;
import net.mureng.api.member.service.MemberSignupService;
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
    private final MemberSignupService memberSignupService;
    private final MemberMapper memberMapper;
    private final OAuth2Service oAuth2Service;

    @ApiOperation(value = "신규 회원 가입", notes = "신규 회원가입입니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResult<MemberDto.ReadOnly>> signup(
            @ApiParam(value = "신규 회원 정보", required = true)
            @RequestBody @Valid MemberDto memberDto) {

        Member newMember = memberMapper.toEntity(memberDto);
        return ResponseEntity.ok(ApiResult.ok(memberMapper.toDto(
                memberSignupService.signup(newMember)
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
    public ResponseEntity<ApiResult<UserCheckDto>> userExists(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "액세스 토큰", required = true) @RequestBody @Valid TokenDto token) throws JsonProcessingException {

        OAuth2Profile profile = oAuth2Service.getProfile(provider, token.getAccessToken());
        return ResponseEntity.ok(ApiResult.ok(
                new UserCheckDto(memberService.isEmailExist(profile.getEmail()), profile.getEmail())
        ));
    }

    @ApiOperation(value = "로그인한 사용자 가져오기", notes = "현재 로그인한 사용자를 가져옵니다.")
    @GetMapping("/me")
    public ResponseEntity<ApiResult<MemberDto.ReadOnly>> me(@CurrentUser Member member) {
        return ResponseEntity.ok(ApiResult.ok(memberMapper.toDto(member)));
    }

    @ApiOperation(value = "특정 사용자의 프로필 가져오기", notes = "특정 사용자의 프로필 정보를 가져옵니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResult<MemberDto.ReadOnly>> getMemberProfile(@CurrentUser Member member,
            @ApiParam(value = "사용자 id", required = true) @PathVariable Long memberId ) {
        return ResponseEntity.ok(ApiResult.ok(
                memberMapper.toDto(memberService.findById(memberId))
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
    public static class UserCheckDto {
        private final boolean exist;
        private final String email;
    }
}
