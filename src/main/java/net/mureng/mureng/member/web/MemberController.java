package net.mureng.mureng.member.web;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping("/signup")
    public ResponseEntity<ApiResult<MemberDto>> signup(@RequestBody MemberDto memberDto) {
        Member member = memberMapper.map(memberDto);
        return ResponseEntity.ok(ApiResult.ok(
                memberMapper.map(memberService.signup(member))));
    }
}
