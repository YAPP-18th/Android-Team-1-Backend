package net.mureng.mureng.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.annotation.CurrentUser;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.jwt.dto.TokenDto;
import net.mureng.mureng.core.oauth2.dto.OAuth2Profile;
import net.mureng.mureng.core.oauth2.service.OAuth2Service;
import net.mureng.mureng.member.component.MemberMapper;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.service.MemberService;
import net.mureng.mureng.member.service.MemberSignupService;
import net.mureng.mureng.reply.component.ReplyMapper;
import net.mureng.mureng.reply.dto.ReplyDto;
import net.mureng.mureng.reply.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "회원 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberReplyController {
    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    @ApiOperation(value = "사용자가 답변한 질문 목록 조회", notes = "사용자가 답변한 질문 목록을 가져옵니다.")
    @GetMapping("/replies")
    public ResponseEntity<ApiResult<List<ReplyDto.ReadOnly>>> getQuestionMemberReplied(
            @CurrentUser Member member){
        return ResponseEntity.ok(
                ApiResult.ok(
                        replyService.findRepliesByMemberId(member.getMemberId()).stream()
                        .map(replyMapper::toDto)
                        .collect(Collectors.toList())
                )
        );
    }

    @ApiOperation(value = "오늘 사용자의 답변 유무 조회", notes = "사용자가 오늘 답변을 했는 지 확인합니다.")
    @GetMapping("/check-replied-today")
    public ResponseEntity<ApiResult<RepliedCheckDto>> isMemberRepliedToday(@CurrentUser Member member) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        new RepliedCheckDto(replyService.isAlreadyReplied(member.getMemberId()))
                )
        );
    }


    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class RepliedCheckDto {
        private final boolean replied;
    }
}
