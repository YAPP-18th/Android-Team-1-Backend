package net.mureng.api.reply.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.reply.component.ReplyLikesMapper;
import net.mureng.core.member.entity.Member;
import net.mureng.core.reply.service.ReplyLikesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "답변 좋아요 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyLikesController {

    private final ReplyLikesService replyLikesService;
    private final ReplyLikesMapper replyLikesMapper;

    @ApiOperation(value = "답변 좋아요", notes = "해당 답변에 좋아요를 추가합니다.")
    @PostMapping("/{replyId}/reply-likes")
    public ResponseEntity<ApiResult> postReplyLikes(@CurrentUser Member member, @PathVariable Long replyId){
        return ResponseEntity.ok(ApiResult.ok(
                replyLikesMapper.toDto(
                        replyLikesService.postReplyLikes(member, replyId)
                )
        ));
    }

    @ApiOperation(value = "답변 좋아요 취소", notes = "해당 답변에 좋아요를 취소합니다.")
    @DeleteMapping("/{replyId}/reply-likes")
    public ResponseEntity<ApiResult<DeletedDto>> deleteReplyLikes(@CurrentUser Member member, @PathVariable Long replyId){
        replyLikesService.deleteReplyLikes(member, replyId);
        return ResponseEntity.ok(ApiResult.ok(new DeletedDto(true)));
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class DeletedDto{
        private final boolean deleted;
    }

}
