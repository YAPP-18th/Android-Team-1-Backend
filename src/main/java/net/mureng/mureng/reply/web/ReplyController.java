package net.mureng.mureng.reply.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.annotation.CurrentUser;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.reply.component.ReplyMapper;
import net.mureng.mureng.reply.dto.ReplyDto;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(value = "답변 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyMapper replyMapper;
    private final ReplyService replyService;

    @ApiOperation(value = "답변 작성하기", notes = "현재 질문에 대한 답변을 작성합니다.")
    @PostMapping("/{questionId}")
    public ResponseEntity<ApiResult> postReply(@CurrentUser Member member,
                                               @RequestBody @Valid ReplyDto replyDto,
                                               @PathVariable @NotNull Long questionId){
        Reply newReply = replyMapper.map(replyDto);

        return ResponseEntity.ok(ApiResult.ok(replyMapper.map(
                replyService.postReply(member, questionId, newReply)
        )));
    }

    @ApiOperation(value = "답변 수정하기", notes = "답변을 수정합니다.")
    @PatchMapping("/{questionId}")
    public ResponseEntity<ApiResult> patchReply(@CurrentUser Member member,
                                                @RequestBody @Valid ReplyDto replyDto,
                                                @PathVariable @NotNull Long questionId){

        Reply newReply = replyMapper.map(replyDto);

        return ResponseEntity.ok(ApiResult.ok(replyMapper.map(
                replyService.patchReply(member, questionId, newReply)
        )));
    }
}
