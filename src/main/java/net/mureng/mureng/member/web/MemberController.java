package net.mureng.mureng.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.member.component.MemberMapper;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.service.MemberService;
import net.mureng.mureng.web.HomeController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "회원 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @ApiOperation(value = "신규 회원 가입", notes = "신규 회원가입입니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResult<MemberDto>> signup(@ApiParam(value = "신규 회원 정보", required = true) @RequestBody MemberDto memberDto) {
        Member member = memberMapper.map(memberDto);
        return ResponseEntity.ok(ApiResult.ok(
                memberMapper.map(memberService.signup(member))));
    }
}
