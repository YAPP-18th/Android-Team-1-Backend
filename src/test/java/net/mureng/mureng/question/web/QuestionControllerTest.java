package net.mureng.mureng.question.web;

import net.mureng.mureng.annotation.WithMockMurengUser;
import net.mureng.mureng.common.EntityCreator;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.service.QuestionService;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestionControllerTest extends AbstractControllerTest {
    @MockBean
    private QuestionService questionService;

    private static final Long QUESTION_ID = 1L;

    @Nested
    @DisplayName("정렬 페이징 질문 목록 조회")
    public class getSortedQuestionList {
        @Test
        @WithMockMurengUser
        public void 질문_목록_인기순_페이징_조회_테스트() throws Exception {
            int page = 0;
            int size = 2;
            List<Reply> replies = new ArrayList<>();
            replies.add(EntityCreator.createReplyEntity());
            replies.add(EntityCreator.createReplyEntity());

            Question popularQuestion = EntityCreator.createQuestionEntity();
            popularQuestion.setQuestionId(2L);
            popularQuestion.setReplies(replies);

            List<Question> questionList = new ArrayList<>();
            questionList.add(popularQuestion);
            questionList.add(EntityCreator.createQuestionEntity());

            Page<Question> questionPage = new PageImpl<>(questionList);

            given(questionService.getQuestionList(eq(page), eq(size), eq("popular"))).willReturn(questionPage);

            mockMvc.perform(
                    get("/api/questions?page=0&size=2&sort=popular")
            ).andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].questionId").value(2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].questionId").value(1))
                    .andDo(print());
        }

        @Test
        @WithMockMurengUser
        public void 질문_목록_최신순_페이징_조회_테스트() throws Exception {
            int page = 0;
            int size = 2;

            Question popularQuestion = EntityCreator.createQuestionEntity();
            popularQuestion.setQuestionId(2L);
            popularQuestion.setRegDate(LocalDateTime.parse("2020-10-11T12:00:00"));

            List<Question> questionList = new ArrayList<>();
            questionList.add(popularQuestion);
            questionList.add(EntityCreator.createQuestionEntity());

            Page<Question> questionPage = new PageImpl<>(questionList);

            given(questionService.getQuestionList(eq(page), eq(size), eq("newest"))).willReturn(questionPage);

            mockMvc.perform(
                    get("/api/questions?page=0&size=2&sort=newest")
            ).andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].questionId").value(2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].questionId").value(1))
                    .andDo(print());
        }
    }

    @Test
    @WithMockMurengUser
    public void 질문_아이디_조회_테스트() throws Exception {
        Question question = EntityCreator.createQuestionEntity();

        given(questionService.getQuestionById(eq(QUESTION_ID))).willReturn(question);

        mockMvc.perform(
                get("/api/questions/1")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questionId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("This is english content."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.koContent").value("이것은 한글 내용입니다."))
                .andDo(print());
    }
}
