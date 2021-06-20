package net.mureng.api.web;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.member.dto.MemberDto;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.service.FcmTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FcmTokenController {
    private final FcmTokenService fcmTokenService;

    @ApiOperation(value = "FCM 토큰 삽입", notes = "FCM 토큰 삽입합니다.")
    @PostMapping("/fcm-token")
    public ResponseEntity<ApiResult<MemberDto.ReadOnly>> fcmToken(@ApiParam(value = "Fcm 토큰 정보", required = true)
                                                                  @RequestBody FcmToken fcmToken) {
        fcmTokenService.insertToken(fcmToken.getFcmToken());
        return ResponseEntity.ok(ApiResult.ok(null));
    }

    @ApiOperation(value = "로그인 사용자 FCM 토큰 갱신", notes = "로그인 사용자 FCM 토큰을 갱신합니다.")
    @PutMapping("member/me/fcm-token")
    public ResponseEntity<ApiResult<MemberDto.ReadOnly>> fcmTokenMe(@CurrentUser Member member,
                                                                  @ApiParam(value = "Fcm 토큰 정보", required = true)
                                                                  @RequestBody FcmToken fcmToken) {
        fcmTokenService.updateTokenOfMember(fcmToken.getFcmToken(), member);
        return ResponseEntity.ok(ApiResult.ok(null));
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class FcmToken {
        @ApiModelProperty(value = "FCM 토큰 값")
        private String fcmToken;
    }
}
