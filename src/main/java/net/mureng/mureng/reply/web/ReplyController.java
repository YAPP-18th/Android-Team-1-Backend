package net.mureng.mureng.reply.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.annotation.CurrentUser;
import net.mureng.mureng.core.dto.ApiPageRequest;
import net.mureng.mureng.core.dto.ApiPageResult;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.reply.component.ReplyMapper;
import net.mureng.mureng.reply.dto.ReplyDto;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "답변 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyMapper replyMapper;
    private final ReplyService replyService;

    @ApiOperation(value = "답변 목록 가져오기", notes = "전체 답변 목록을 페이징으로 가져옵니다.")
    @GetMapping
    public ResponseEntity<ApiPageResult<ReplyDto.ReadOnly>> get(
            ApiPageRequest pageRequest,
            @CurrentUser Member member
    ){

        return ResponseEntity.ok(ApiPageResult.ok(
                replyService.findReplies(pageRequest)
                .map(x -> replyMapper.toDto(x, member))
        ));
    }

    @ApiOperation(value = "답변 작성하기", notes = "현재 질문에 대한 답변을 작성합니다.")
    @PostMapping
    public ResponseEntity<ApiResult<ReplyDto.ReadOnly>> create(@CurrentUser Member member,
                                            @RequestBody @Valid ReplyDto replyDto){

        Reply newReply = replyMapper.toEntity(replyDto);
        newReply.setAuthor(member);
        newReply.setQuestion(Question.builder().questionId(replyDto.getQuestionId()).build());

        return ResponseEntity.ok(ApiResult.ok(replyMapper.toDto(
                replyService.create(newReply), member
        )));
    }

    @ApiOperation(value = "답변 수정하기", notes = "답변을 수정합니다.")
    @PutMapping("/{replyId}")
    public ResponseEntity<ApiResult<ReplyDto.ReadOnly>> update(@CurrentUser Member member,
                                            @RequestBody @Valid ReplyDto replyDto,
                                            @PathVariable @NotNull Long replyId){

        Reply newReply = replyMapper.toEntity(replyDto);
        newReply.setAuthor(member);
        newReply.setReplyId(replyId);

        return ResponseEntity.ok(ApiResult.ok(replyMapper.toDto(
                replyService.update(newReply), member
        )));
    }

    @ApiOperation(value = "답변 삭제하기", notes = "답변을 삭제합니다.")
    @DeleteMapping("/{replyId}")
    public ResponseEntity<ApiResult<Boolean>> deleteReply(@CurrentUser Member member,
                                                 @PathVariable @NotNull Long replyId){
        replyService.delete(member, replyId);
        return ResponseEntity.ok(ApiResult.ok(true));
    }
}
