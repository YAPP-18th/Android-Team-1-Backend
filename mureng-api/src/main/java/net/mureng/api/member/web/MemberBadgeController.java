package net.mureng.api.member.web;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.member.service.MemberBadgeService;
import net.mureng.core.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "사용자 뱃지 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberBadgeController {
    private final MemberBadgeService memberBadgeService;

    @PutMapping("/me/check/badge/{badgeId}")
    public ResponseEntity<ApiResult<BadgeAccomplishedCheckDto>> updateBadgeAccomplished(@CurrentUser Member member, @PathVariable Long badgeId) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        new BadgeAccomplishedCheckDto(memberBadgeService.updateBadgeAccomplished(member.getMemberId(), badgeId))
                )
        );
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class BadgeAccomplishedCheckDto {
        private final boolean isChecked;
    }
}
