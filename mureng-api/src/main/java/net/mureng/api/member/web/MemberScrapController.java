package net.mureng.api.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.member.component.MemberScrapMapper;
import net.mureng.api.member.service.MemberExpressionScrapService;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;
import net.mureng.core.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "회원 오늘의 표현 스크랩 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberScrapController {
    private final MemberExpressionScrapService memberExpressionScrapService;
    private final MemberScrapMapper memberScrapMapper;

    @ApiOperation(value = "오늘의 표현 스크랩", notes = "오늘의 표현을 스크랩합니다.")
    @PostMapping("/today-expression/{expId}")
    public ResponseEntity<ApiResult<TodayExpressionDto>> scrap(@CurrentUser Member member, @PathVariable Long expId){
        return ResponseEntity.ok(ApiResult.ok(
                memberScrapMapper.toDto(
                    memberExpressionScrapService.scrapTodayExpression(member, expId)
                )
        ));
    }

}
