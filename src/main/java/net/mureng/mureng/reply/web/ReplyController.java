package net.mureng.mureng.reply.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.annotation.CurrentUser;
import net.mureng.mureng.core.dto.ApiPageRequest;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.reply.component.ReplyMapper;
import net.mureng.mureng.reply.dto.ReplyDto;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.service.ReplyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ApiResult<List<ReplyDto>>> get(ApiPageRequest pageRequest,
                                                         @ApiParam(value = "페이지 정렬 방식(popular, newest)")
                                                         @RequestParam(required = false, defaultValue = "popular") String sort){

        return ResponseEntity.ok(ApiResult.ok(
                replyService.findReplies(pageRequest.convert(), sort).stream()
                .map(replyMapper::map)
                .collect(Collectors.toList())
        ));
    }

    @ApiOperation(value = "답변 작성하기", notes = "현재 질문에 대한 답변을 작성합니다.")
    @PostMapping
    public ResponseEntity<ApiResult<ReplyDto>> create(@CurrentUser Member member,
                                            @RequestBody @Valid ReplyDto replyDto){

        Reply newReply = replyMapper.map(replyDto);
        newReply.setMember(member);
        newReply.setQuestion(Question.builder().questionId(replyDto.getQuestionId()).build());

        return ResponseEntity.ok(ApiResult.ok(replyMapper.map(
                replyService.create(newReply)
        )));
    }

    @ApiOperation(value = "답변 수정하기", notes = "답변을 수정합니다.")
    @PutMapping("/{replyId}")
    public ResponseEntity<ApiResult<ReplyDto>> update(@CurrentUser Member member,
                                            @RequestBody @Valid ReplyDto replyDto,
                                            @PathVariable @NotNull Long replyId){

        Reply newReply = replyMapper.map(replyDto);
        newReply.setMember(member);
        newReply.setReplyId(replyId);

        return ResponseEntity.ok(ApiResult.ok(replyMapper.map(
                replyService.update(newReply)
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
