package net.mureng.mureng.reply.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.annotation.CurrentUser;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.member.entity.Member;
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

@Api(value = "답변 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyMapper replyMapper;
    private final ReplyService replyService;

    @ApiOperation(value = "답변 작성하기", notes = "현재 질문에 대한 답변을 작성합니다.")
    @PostMapping
    public ResponseEntity<ApiResult<ReplyDto>> create(@CurrentUser Member member,
                                            @RequestBody @Valid ReplyDto replyDto){

        Reply newReply = replyMapper.map(replyDto);

        return ResponseEntity.ok(ApiResult.ok(replyMapper.map(
                replyService.create(member, replyDto.getQuestionId(), newReply)
        )));
    }

    @ApiOperation(value = "답변 수정하기", notes = "답변을 수정합니다.")
    @PutMapping("/{replyId}")
    public ResponseEntity<ApiResult<ReplyDto>> update(@CurrentUser Member member,
                                            @RequestBody @Valid ReplyDto replyDto,
                                            @PathVariable @NotNull Long replyId){

        Reply newReply = replyMapper.map(replyDto);

        return ResponseEntity.ok(ApiResult.ok(replyMapper.map(
                replyService.update(member, replyId, newReply)
        )));
    }

    @ApiOperation(value = "답변 삭제하기", notes = "답변을 삭제합니다.")
    @DeleteMapping("/{replyId}")
    public ResponseEntity<ApiResult<Boolean>> deleteReply(@CurrentUser Member member,
                                                 @PathVariable @NotNull Long replyId){
        replyService.delete(member, replyId);
        return ResponseEntity.ok(ApiResult.ok(true));
    }

    @ApiOperation(value = "답변 이미지 업로드", notes = "답변에 적용될 이미지를 업로드합니다.")
    @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResult<ImagePathDto>> postImage(
            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(ApiResult.ok(new ImagePathDto(replyService.uploadReplyImageFile(image))));
    }

    @Getter
    @AllArgsConstructor
    public static class ImagePathDto {
        private final String imagePath;
    }
}
