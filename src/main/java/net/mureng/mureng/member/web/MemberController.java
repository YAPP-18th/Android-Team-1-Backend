package net.mureng.mureng.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.dto.ApiResult;
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

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class DuplicatedCheckDto {
        private final boolean duplicated;
    }
}
