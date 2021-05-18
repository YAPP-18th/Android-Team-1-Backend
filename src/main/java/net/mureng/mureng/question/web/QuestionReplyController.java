package net.mureng.mureng.question.web;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.annotation.CurrentUser;
import net.mureng.mureng.core.dto.ApiPageRequest;
import net.mureng.mureng.core.dto.ApiPageResult;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.component.QuestionMapper;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.service.QuestionService;
import net.mureng.mureng.reply.component.ReplyMapper;
import net.mureng.mureng.reply.dto.ReplyDto;
import net.mureng.mureng.reply.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "질문에 대한 답변 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionReplyController {
    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    @ApiOperation(value = "질문 관련 답변 가져오기", notes = "질문 관련 답변 가져오기")
    @GetMapping("/{questionId}/replies")
    public ResponseEntity<ApiPageResult<ReplyDto.ReadOnly>> getQuestionReplies(
            @ApiParam(value = "질문 id" ,required = true) @PathVariable Long questionId, ApiPageRequest pageRequest) {

        return ResponseEntity.ok(
                ApiPageResult.ok(
                        replyService.findRepliesByQuestionId(questionId, pageRequest)
                        .map(replyMapper::toDto))
        );
    }

    @ApiOperation(value = "질문에 대한 내 답변 가져오기", notes = "질문에 대한 내 답변 가져오기")
    @GetMapping("/{questionId}/replies/me")
    @ApiResponses({
            @ApiResponse(code = 404, message = "답변이 존재하지 않습니다.")
    })
    public ResponseEntity<ApiResult<ReplyDto.ReadOnly>> getQuestionRepliesMe(
            @ApiParam(value = "질문 id" ,required = true) @PathVariable Long questionId, @CurrentUser Member member) {

        return ResponseEntity.ok(
                ApiPageResult.ok(replyMapper.toDto(
                        replyService.findReplyByQuestionIdAndMember(member.getMemberId(), questionId)
                ))
        );
    }
}
