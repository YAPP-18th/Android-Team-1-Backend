package net.mureng.mureng.reply.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.annotation.CurrentUser;
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

@Api(value = "답변 이미지 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyImageController {
    private final ReplyService replyService;

    @ApiOperation(value = "답변 이미지 업로드", notes = "답변에 적용될 이미지를 업로드합니다.")
    @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResult<ImagePathDto>> postImage(
            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(ApiResult.ok(new ImagePathDto(replyService.uploadReplyImageFile(image))));
    }

    @ApiOperation(value = "기본 이미지 목록 조회", notes = "기본 제공되는 이미지 경로 목록을 제공합니다. 현재 더미가 제공됩니다.")
    @GetMapping(value = "/default-images")
    public ResponseEntity<ApiResult<List<String>>> getDefaultImages() {
        return ResponseEntity.ok(ApiResult.ok(
                List.of("/reply/1.jpg", "/reply/2.jpg", "/reply/3.jpg") // 더미
        ));
    }

    @Getter
    @AllArgsConstructor
    public static class ImagePathDto {
        private final String imagePath;
    }
}
