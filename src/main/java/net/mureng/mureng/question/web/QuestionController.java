package net.mureng.mureng.question.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.annotation.CurrentUser;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.component.QuestionMapper;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "질문 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @ApiOperation(value = "질문 목록 정렬 페이징 조회", notes = "질문 목록을 정렬 페이징해서 가져옵니다.")
    @GetMapping
    public ResponseEntity<ApiResult<List<QuestionDto>>> getQuestionList(
            @ApiParam(value = "페이지 번호" ,required = false) @RequestParam(required = false, defaultValue = "0") int page,
            @ApiParam(value = "한 페이지 크기" ,required = false) @RequestParam(required = false, defaultValue = "10") int size,
            @ApiParam(value = "페이지 정렬 방식(popular, newest)" ,required = false) @RequestParam(required = false, defaultValue = "popular") String sort){

        Page<Question> questionList = questionService.getQuestionList(page, size, sort);

        return ResponseEntity.ok(ApiResult.ok(
                questionList.stream()
                .map(questionMapper::map)
                .collect(Collectors.toList())
        ));
    }

    @ApiOperation(value = "질문 조회", notes = "해당 질문을 가져옵니다.")
    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResult<QuestionDto>> getQuestionById(
            @ApiParam(value = "질문 id" ,required = true) @PathVariable @NotNull Long questionId){

        return ResponseEntity.ok(ApiResult.ok(
                questionMapper.map(questionService.getQuestionById(questionId))
        ));
    }

    @ApiOperation(value = "내가 만든 질문 목록 조회", notes = "해당 사용자가 만든 질문 목록을 가져옵니다.")
    @GetMapping("/me")
    public ResponseEntity<ApiResult<List<QuestionDto>>> getQuestionWrittenByMe(@CurrentUser Member member){
        List<Question> questionList =  questionService.getQuestionWrittenByMember(member.getMemberId());

        return ResponseEntity.ok(ApiResult.ok(
                questionList.stream()
                .map(questionMapper::map)
                .collect(Collectors.toList())
        ));
    }


}
