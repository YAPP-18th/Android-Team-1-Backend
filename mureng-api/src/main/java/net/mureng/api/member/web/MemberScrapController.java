package net.mureng.api.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.member.component.MemberScrapMapper;
import net.mureng.api.member.dto.MemberScrapDto;
import net.mureng.api.member.service.MemberExpressionScrapService;
import net.mureng.api.todayexpression.component.TodayExpressionMapper;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;
import net.mureng.core.badge.service.BadgeAccomplishedService;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.entity.MemberScrap;
import net.mureng.core.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "회원 오늘의 표현 스크랩 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberScrapController {
    private final MemberExpressionScrapService memberExpressionScrapService;
    private final TodayExpressionMapper todayExpressionMapper;
    private final MemberScrapMapper memberScrapMapper;
    private final MemberService memberService;
    private final BadgeAccomplishedService badgeAccomplishedService;

    @ApiOperation(value = "오늘의 표현 스크랩", notes = "오늘의 표현을 스크랩합니다.")
    @PostMapping("/scrap/{expId}")
    public ResponseEntity<ApiResult<TodayExpressionDto>> scrap(@CurrentUser Member member, @PathVariable Long expId){
        return ResponseEntity.ok(ApiResult.ok(
                todayExpressionMapper.toDto(
                    memberExpressionScrapService.scrapTodayExpression(member, expId).getTodayExpression(), member,
                        badgeAccomplishedService.createAcademicMureng(member.getMemberId())
                )
        ));
    }

    @ApiOperation(value = "오늘의 표현 스크랩 취소", notes = "오늘의 표현 스크랩한 것을 취소합니다.")
    @DeleteMapping("/scrap/{expId}")
    public ResponseEntity<ApiResult<DeletedDto>> deleteScrap(@CurrentUser Member member, @PathVariable Long expId){
        memberExpressionScrapService.deleteScrap(member, expId);

        return ResponseEntity.ok(ApiResult.ok(new DeletedDto(true)));
    }

    @ApiOperation(value = "사용자의 스크랩 목록 가져오기", notes = "사용자의 스크랩 목록을 가져옵니다.")
    @GetMapping("/{memberId}/scrap")
    public ResponseEntity<ApiResult<MemberScrapDto>> getMemberScrap(@CurrentUser Member member, @PathVariable Long memberId){

        List<MemberScrap> scrapList = memberExpressionScrapService.getMemberScrap(memberId);
        Member profileMember = memberService.findById(memberId);

        return ResponseEntity.ok(ApiResult.ok(
                memberScrapMapper.toDto(
                        profileMember,
                        scrapList.stream().map(MemberScrap::getTodayExpression).collect(Collectors.toList()),
                        member)
        ));
    }

    @ApiOperation(value = "내 스크랩 목록 가져오기", notes ="나의 스크랩 목록을 가져옵니다.")
    @GetMapping("/me/scrap")
    public ResponseEntity<ApiResult<MemberScrapDto>> getMyScrap(@CurrentUser Member member){
        long memberId = member.getMemberId();

        List<MemberScrap> scrapList = memberExpressionScrapService.getMemberScrap(memberId);

        return ResponseEntity.ok(ApiResult.ok(
                memberScrapMapper.toDto(
                        member,
                        scrapList.stream().map(MemberScrap::getTodayExpression).collect(Collectors.toList()),
                        member)
        ));
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class DeletedDto{
        private final boolean deleted;
    }
}
