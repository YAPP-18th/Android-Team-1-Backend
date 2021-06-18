package net.mureng.api.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.*;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.member.component.MemberMapper;
import net.mureng.api.member.dto.MemberDto;
import net.mureng.api.member.service.MemberSignupService;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value = "회원 설정 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberSettingController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @ApiOperation(value = "좋아요 Push 알림 ON", notes = "좋아요 Push 알림을 켭니다.")
    @PostMapping("/me/setting/push/like")
    public ResponseEntity<ApiResult<MemberDto.ReadOnly>> likePushOn(@CurrentUser Member member) {
        member.getMemberSetting().setLikePushActive(true);
        return ResponseEntity.ok(ApiResult.ok(memberMapper.toDto(
                memberService.saveMember(member)
        )));
    }

    @ApiOperation(value = "좋아요 Push 알림 OFF", notes = "좋아요 Push 알림을 끕니다.")
    @DeleteMapping("/me/setting/push/like")
    public ResponseEntity<ApiResult<MemberDto.ReadOnly>> likePushOff(@CurrentUser Member member) {
        member.getMemberSetting().setLikePushActive(false);
        return ResponseEntity.ok(ApiResult.ok(memberMapper.toDto(
                memberService.saveMember(member)
        )));
    }

    @ApiOperation(value = "데일리 Push 알림 ON", notes = "데일리 Push 알림을 켭니다.")
    @PostMapping("/me/setting/push/daily")
    public ResponseEntity<ApiResult<MemberDto.ReadOnly>> dailyPushOn(@CurrentUser Member member) {
        member.getMemberSetting().setDailyPushActive(true);
        return ResponseEntity.ok(ApiResult.ok(memberMapper.toDto(
                memberService.saveMember(member)
        )));
    }

    @ApiOperation(value = "데일리 Push 알림 OFF", notes = "데일리 Push 알림을 끕니다.")
    @DeleteMapping("/me/setting/push/daily")
    public ResponseEntity<ApiResult<MemberDto.ReadOnly>> dailyPushOff(@CurrentUser Member member) {
        member.getMemberSetting().setDailyPushActive(false);
        return ResponseEntity.ok(ApiResult.ok(memberMapper.toDto(
                memberService.saveMember(member)
        )));
    }

    @NoArgsConstructor
    @Getter @Setter
    public static class FcmToken {
        private String fcmToken;
    }
}
