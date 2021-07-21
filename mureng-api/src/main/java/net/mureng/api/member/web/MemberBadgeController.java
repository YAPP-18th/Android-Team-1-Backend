package net.mureng.api.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.member.service.MemberBadgeService;
import net.mureng.core.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static net.mureng.core.core.message.ResponseMessage.CHECK_BADGE_ACCOMPLISHED;

@Api(value = "사용자 뱃지 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberBadgeController {
    private final MemberBadgeService memberBadgeService;

    @ApiOperation(value = "사용자의 뱃지 획득 확인 엔드포인트", notes = "사용자가 해당 뱃지 획득을 확인했음을 서버에 전달합니다.")
    @PutMapping("/me/check/badge/{badgeId}")
    public ResponseEntity<ApiResult> updateBadgeAccomplished(@CurrentUser Member member, @PathVariable Long badgeId) {
        memberBadgeService.updateBadgeAccomplishedChecked(member.getMemberId(), badgeId);

        return ResponseEntity.ok(ApiResult.ok(null, CHECK_BADGE_ACCOMPLISHED));
    }

    @ApiOperation(value = "사용자의 뱃지 획득 여부 엔드포인트", notes = "사용자가 해당 뱃지를 획득했는 지 가져옵니다.")
    @GetMapping("/me/check/badge/{badgeId}")
    public ResponseEntity<ApiResult<BadgeAccomplishedCheckDto>> checkIsBadgeAccomplished(@CurrentUser Member member, @PathVariable Long badgeId){
        return ResponseEntity.ok(ApiResult.ok(
                new BadgeAccomplishedCheckDto(
                memberBadgeService.checkIsBadgeAccomplished(member.getMemberId(), badgeId)
            )
        ));
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class BadgeAccomplishedCheckDto {
        private final boolean isChecked;
    }
}
