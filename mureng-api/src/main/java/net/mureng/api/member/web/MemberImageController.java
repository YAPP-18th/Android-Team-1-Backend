package net.mureng.api.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.core.jwt.dto.TokenDto;
import net.mureng.api.core.oauth2.dto.OAuth2Profile;
import net.mureng.api.core.oauth2.service.OAuth2Service;
import net.mureng.api.member.component.MemberMapper;
import net.mureng.api.member.dto.MemberDto;
import net.mureng.api.member.service.MemberImageService;
import net.mureng.api.member.service.MemberSignupService;
import net.mureng.api.reply.web.ReplyImageController;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.service.MemberService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value = "회원 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberImageController {
    private final MemberImageService memberImageService;

    @ApiOperation(value = "사용자 이미지 업로드", notes = "사용자 프로필 이미지를 업로드합니다.")
    @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResult<ImagePathDto>> postImage(
            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(ApiResult.ok(new ImagePathDto(memberImageService.uploadMemberImageFile(image))));
    }

    @Getter
    @AllArgsConstructor
    public static class ImagePathDto {
        private final String imagePath;
    }
}
