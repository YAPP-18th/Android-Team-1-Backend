package net.mureng.core.question.service;

import net.mureng.core.common.EntityCreator;
import net.mureng.core.core.component.NumberRandomizer;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.entity.Question;
import net.mureng.core.question.repository.QuestionRepository;
import net.mureng.core.question.service.QuestionService;
import net.mureng.core.question.service.TodayQuestionSelectionServiceImpl;
import net.mureng.core.question.service.TodayQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TodayQuestionSelectionServiceImplTest {
    private final Member member = EntityCreator.createMemberEntity();
    private final Question question1 = EntityCreator.createQuestionEntity();
    private final Question question2 = EntityCreator.createQuestionEntity();
    private final Question question3 = EntityCreator.createQuestionEntity();
    private final Question question5 = EntityCreator.createQuestionEntity();

    @InjectMocks
    private TodayQuestionSelectionServiceImpl todayQuestionSelectionService;

    @Mock
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private TodayQuestionService todayQuestionService;

    @Mock
    private NumberRandomizer numberRandomizer;

    @BeforeEach
    public void setUp() {
        question2.setQuestionId(2L);
        question3.setQuestionId(3L);
        question5.setQuestionId(5L);

        given(todayQuestionService.getTodayQuestionByMemberId(eq(member.getMemberId()))).willReturn(question1);
        given(questionRepository.findAll(eq(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "regDate")))))
                .willReturn(new PageImpl<>(List.of(question5)));
        given(questionService.isAlreadyReplied(eq(question1.getQuestionId()), eq(member.getMemberId()))).willReturn(false);
        given(questionService.isAlreadyReplied(eq(question2.getQuestionId()), eq(member.getMemberId()))).willReturn(true);
        given(questionService.isAlreadyReplied(eq(question3.getQuestionId()), eq(member.getMemberId()))).willReturn(false);
        given(questionService.isAlreadyReplied(eq(question5.getQuestionId()), eq(member.getMemberId()))).willReturn(true);
        given(numberRandomizer.getRandomInt(anyInt())).willReturn(2, 1, 4, 5, 3);
        given(questionService.existsById(eq(1L))).willReturn(true);
        given(questionService.existsById(eq(2L))).willReturn(true);
        given(questionService.existsById(eq(3L))).willReturn(true);
        given(questionService.existsById(eq(4L))).willReturn(false);
        given(questionService.existsById(eq(5L))).willReturn(true);
        given(questionService.getQuestionById(eq(1L))).willReturn(question1);
        given(questionService.getQuestionById(eq(2L))).willReturn(question2);
        given(questionService.getQuestionById(eq(3L))).willReturn(question3);
        given(questionService.getQuestionById(eq(4L))).willThrow(BadRequestException.class);
        given(questionService.getQuestionById(eq(5L))).willReturn(question5);
    }

    @Test
    public void 오늘의_질문_새로고침_테스트() {
        Question reselectedQuestion = todayQuestionSelectionService.reselectTodayQuestion(member);
        assertEquals(3L, reselectedQuestion.getQuestionId());
    }

    @Test
    public void 오늘의_질문_새로고침_모두답변_테스트() {
        given(questionService.isAlreadyReplied(eq(question3.getQuestionId()), eq(member.getMemberId()))).willReturn(true);

        Question reselectedQuestion = todayQuestionSelectionService.reselectTodayQuestion(member);
        assertEquals(1L, reselectedQuestion.getQuestionId()); // 처음 설정 그대로 가져간다.
    }
}