package net.mureng.mureng.question.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.question.component.QuestionMapper;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "질문 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

//    @ApiOperation(value = "질문에 대한 답변 목록 가져오기", notes = "질문에 대한 답변 목록을 가져옵니다.")
    @ApiOperation(value = "질문 목록 가져오기", notes = "질문 목록 가져오기")
    @GetMapping
    public ResponseEntity<ApiResult<List<QuestionDto>>> getQuestionList(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size ){

        Page<Question> questionList = questionService.getQuestionList(page, size);

        return ResponseEntity.ok(ApiResult.ok(
                questionList.stream()
                .map(questionMapper::map)
                .collect(Collectors.toList())
        ));
    }
}
