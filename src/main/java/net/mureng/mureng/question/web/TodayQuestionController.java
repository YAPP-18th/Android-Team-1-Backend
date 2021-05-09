package net.mureng.mureng.question.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.annotation.CurrentUser;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.dto.UserDetailsImpl;
import net.mureng.mureng.member.component.MemberMapper;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.service.MemberService;
import net.mureng.mureng.question.component.QuestionMapper;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.service.TodayQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

@Api(value = "오늘의 질문 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/today-question")
public class TodayQuestionController {
    private final TodayQuestionService todayQuestionService;
    private final QuestionMapper questionMapper;

    @ApiOperation(value = "오늘의 질문 가져오기", notes = "현재 로그인한 사용자의 오늘의 질문을 가져옵니다.")
    @GetMapping("")
    public ResponseEntity<ApiResult<QuestionDto>> get(@CurrentUser Member member) {
        return ResponseEntity.ok(ApiResult.ok(questionMapper.map(
                todayQuestionService.getTodayQuestionByMemberId(member.getMemberId())
        )));
    }
}
