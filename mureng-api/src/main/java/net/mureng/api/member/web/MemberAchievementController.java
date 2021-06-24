package net.mureng.api.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.member.component.MemberAchievementMapper;
import net.mureng.api.member.dto.MemberAchievementDto;
import net.mureng.api.member.service.MemberBadgeService;
import net.mureng.core.badge.entity.Badge;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.service.BadgeAccomplishedService;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "사용자 성과 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberAchievementController {
    private final MemberBadgeService memberBadgeService;
    private final MemberAchievementMapper memberAchievementMapper;
    private final MemberService memberService;
    private final BadgeAccomplishedService badgeAccomplishedService;

    @ApiOperation(value = "사용자의 성과 페이지", notes = "사용자의 뱃지 목록을 가져옵니다.")
    @GetMapping("/{memberId}/achievement")
    public ResponseEntity<ApiResult<MemberAchievementDto>> getMemberAchievement(@CurrentUser Member member, @PathVariable Long memberId) {

        List<Badge> badges = memberBadgeService.getMemberBadges(memberId).stream().map(BadgeAccomplished::getBadge).collect(Collectors.toList());
        Member profileMember = memberService.findById(memberId);

        return ResponseEntity.ok(ApiResult.ok(
                memberAchievementMapper.toDto(profileMember, badges, member, badgeAccomplishedService.isAlreadyCheckedCelebrityMureng(memberId))
        ));
    }
}
