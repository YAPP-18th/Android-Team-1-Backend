package net.mureng.api.reply.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiPageRequest;
import net.mureng.api.core.dto.ApiPageResult;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.reply.service.ReplyPaginationService;
import net.mureng.core.badge.service.BadgeAccomplishedService;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.entity.Question;
import net.mureng.api.reply.component.ReplyMapper;
import net.mureng.api.reply.dto.ReplyDto;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(value = "답변 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyMapper replyMapper;

    private final ReplyService replyService;
    private final ReplyPaginationService replyPaginationService;
    private final BadgeAccomplishedService badgeAccomplishedService;

    @ApiOperation(value = "답변 목록 가져오기", notes = "전체 답변 목록을 페이징으로 가져옵니다.")
    @GetMapping
    public ResponseEntity<ApiPageResult<ReplyDto.ReadOnly>> get(ApiPageRequest pageRequest,
                                                                @CurrentUser Member member){
        return ResponseEntity.ok(ApiPageResult.ok(
                replyPaginationService.findReplies(pageRequest)
                .map(x -> replyMapper.toDto(x, member))
        ));
    }

    @ApiOperation(value = "답변 상세 조회하기", notes = "답변에 대한 상세 정보를 가져옵니다.")
    @GetMapping("/{replyId}")
    public ResponseEntity<ApiResult<ReplyDto.ReadOnly>> getReplyInfo(
            @ApiParam(value = "답변 id", required = true) @PathVariable Long replyId,
                                                        @CurrentUser Member member) {
            return ResponseEntity.ok(ApiResult.ok(
                    replyMapper.toDto(replyService.findById(replyId), member)
            ));
        }

    @ApiOperation(value = "답변 작성하기", notes = "현재 질문에 대한 답변을 작성합니다.")
    @PostMapping
    public ResponseEntity<ApiResult<ReplyDto.ReadOnly>> create(@CurrentUser Member member,
                                                               @RequestBody @Valid ReplyDto replyDto){
            Reply newReply = replyMapper.toEntityForPost(replyDto, member, Question.builder().questionId(replyDto.getQuestionId()).build());

            return ResponseEntity.ok(ApiResult.ok(replyMapper.toDto(
                replyService.create(newReply), member
        )));
    }

    @ApiOperation(value = "답변 수정하기", notes = "답변을 수정합니다.")
    @PutMapping("/{replyId}")
    public ResponseEntity<ApiResult<ReplyDto.ReadOnly>> update(@CurrentUser Member member,
                                                               @RequestBody @Valid ReplyDto replyDto,
                                                               @PathVariable @NotNull Long replyId){

        Reply newReply = replyMapper.toEntityForPut(replyDto, member, replyId);

        return ResponseEntity.ok(ApiResult.ok(replyMapper.toDto(
                replyService.update(newReply), member
        )));
    }

    @ApiOperation(value = "답변 삭제하기", notes = "답변을 삭제합니다.")
    @DeleteMapping("/{replyId}")
    public ResponseEntity<ApiResult<DeletedDto>> deleteReply(@CurrentUser Member member,
                                                          @PathVariable @NotNull Long replyId){
        replyService.delete(member, replyId);
        return ResponseEntity.ok(ApiResult.ok(new DeletedDto(true)));
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class DeletedDto{
        private final boolean deleted;
    }
}
