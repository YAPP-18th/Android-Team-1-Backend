package net.mureng.api.question.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.annotation.CurrentUser;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.core.member.entity.Member;
import net.mureng.api.question.component.QuestionMapper;
import net.mureng.api.question.dto.QuestionDto;
import net.mureng.core.question.service.TodayQuestionSelectionService;
import net.mureng.core.question.service.TodayQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "오늘의 질문 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/today-question")
public class TodayQuestionController {
    private final TodayQuestionService todayQuestionService;
    private final TodayQuestionSelectionService todayQuestionSelectionService;
    private final QuestionMapper questionMapper;

    @ApiOperation(value = "오늘의 질문 가져오기", notes = "현재 로그인한 사용자의 오늘의 질문을 가져옵니다.")
    @GetMapping("")
    public ResponseEntity<ApiResult<QuestionDto.ReadOnly>> get(@CurrentUser Member member) {
        return ResponseEntity.ok(ApiResult.ok(questionMapper.toDto(
                todayQuestionService.getTodayQuestionByMemberId(member.getMemberId())
        )));
    }

    @ApiOperation(value = "오늘의 질문 새로고침해서 가져오기", notes = "오늘의 질문을 새로고침 해서 가져옵니다.")
    @GetMapping("/refresh")
    public ResponseEntity<ApiResult<QuestionDto.ReadOnly>> getRefresh(@CurrentUser Member member) {
        return ResponseEntity.ok(ApiResult.ok(questionMapper.toDto(
                todayQuestionSelectionService.reselectTodayQuestion(member)
        )));
    }
}
