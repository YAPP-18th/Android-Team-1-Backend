package net.mureng.mureng.question.web;

import net.mureng.mureng.annotation.WithMockMurengUser;
import net.mureng.mureng.common.EntityCreator;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.service.QuestionService;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.service.ReplyService;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestionControllerTest extends AbstractControllerTest {
    @MockBean
    private QuestionService questionService;

    @MockBean
    private ReplyService replyService;

    private static final Long QUESTION_ID = 1L;

    @Nested
    @DisplayName("정렬 페이징 질문 목록 조회")
    public class getSortedQuestionList {
        @Test
        @WithMockMurengUser
        public void 질문_목록_인기순_페이징_조회_테스트() throws Exception {
            int page = 0;
            int size = 2;
            List<Reply> replies = Arrays.asList(EntityCreator.createReplyEntity(), EntityCreator.createReplyEntity());

            Question popularQuestion = EntityCreator.createQuestionEntity();
            popularQuestion.setQuestionId(2L);
            popularQuestion.setReplies(replies);

            List<Question> questionList = new ArrayList<>();
            questionList.add(popularQuestion);
            questionList.add(EntityCreator.createQuestionEntity());

            Page<Question> questionPage = new PageImpl<>(questionList);

            given(questionService.getQuestionList(eq(PageRequest.of(page, size)), eq("popular"))).willReturn(questionPage);

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

            given(questionService.getQuestionList(eq(PageRequest.of(page, size)), eq("newest"))).willReturn(questionPage);

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

    @Test
    @WithMockMurengUser
    public void 내가_만든_질문_목록_조회_테스트() throws Exception {
        List<Reply> replies = Arrays.asList(EntityCreator.createReplyEntity(), EntityCreator.createReplyEntity());

        Question newQuestion = EntityCreator.createQuestionEntity();
        newQuestion.setContent("new data");
        newQuestion.setReplies(replies);
        newQuestion.setRegDate(LocalDateTime.parse("2020-10-11T12:00:00"));

        List<Question> questionList = new ArrayList<>();
        questionList.add(newQuestion);
        questionList.add(EntityCreator.createQuestionEntity());

        given(questionService.getQuestionWrittenByMember(any())).willReturn(questionList);

        mockMvc.perform(
                get("/api/questions/me")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("new data"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].repliesCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("This is english content."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].repliesCount").value(0))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 질문_관련_답변_목록_가져오기_테스트() throws Exception {
        Reply reply1 = EntityCreator.createReplyEntity();
        reply1.setContent("content1");
        Reply reply2 = EntityCreator.createReplyEntity();
        reply2.setContent("content2");
        reply2.setReplyLikes(new HashSet<>());
        List<Reply> replies = Arrays.asList(reply1, reply2);
        int page = 0;
        int size = 2;

        given(replyService.findRepliesByQuestionId(eq(1L), eq(PageRequest.of(page, size)), any()))
                .willReturn(new PageImpl<>(replies));

        mockMvc.perform(
                get("/api/questions/1/replies?page=0&size=2")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("content1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].replyLikeCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("content2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].replyLikeCount").value(0))
                .andDo(print());
    }
}