package net.mureng.api.member.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.api.member.component.MemberScrapMapper;
import net.mureng.api.member.service.MemberExpressionScrapService;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;
import net.mureng.core.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "회원 오늘의 표현 스크랩 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberScrapController {
    private final MemberExpressionScrapService memberExpressionScrapService;
    private final MemberScrapMapper memberScrapMapper;

    @ApiOperation(value = "오늘의 표현 스크랩", notes = "오늘의 표현을 스크랩합니다.")
    @PostMapping("/scrap/{expId}")
    public ResponseEntity<ApiResult<TodayExpressionDto>> scrap(@CurrentUser Member member, @PathVariable Long expId){
        return ResponseEntity.ok(ApiResult.ok(
                memberScrapMapper.toDto(
                    memberExpressionScrapService.scrapTodayExpression(member, expId)
                )
        ));
    }

    @ApiOperation(value = "오늘의 표현 스크랩 취소", notes = "오늘의 표현 스크랩한 것을 취소합니다.")
    @DeleteMapping("/scrap/{expId}")
    public ResponseEntity<ApiResult<DeletedDto>> deleteScrap(@CurrentUser Member member, @PathVariable Long expId){
        memberExpressionScrapService.deleteScrap(member, expId);

        return ResponseEntity.ok(ApiResult.ok(new DeletedDto(true)));
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class DeletedDto{
        private final boolean deleted;
    }
}
