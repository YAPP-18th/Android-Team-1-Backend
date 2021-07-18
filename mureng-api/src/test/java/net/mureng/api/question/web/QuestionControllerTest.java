package net.mureng.api.question.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.core.dto.ApiPageRequest;
import net.mureng.api.question.service.QuestionPaginationService;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.question.entity.Question;
import net.mureng.core.question.service.QuestionService;
import net.mureng.core.reply.entity.Reply;
import net.mureng.api.web.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestionControllerTest extends AbstractControllerTest {
    private static final Long QUESTION_ID = 1L;

    @Nested
    @DisplayName("정렬 페이징 질문 목록 조회")
    public class getSortedQuestionList {
        @Test
        @WithMockMurengUser
        public void 질문_목록_인기순_페이징_조회_테스트() throws Exception {
            mockMvc.perform(
                    get("/api/questions?page=0&size=2&sort=popular")
            ).andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].questionId").value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].questionId").value(2))
                    .andDo(print());
        }

        @Test
        @WithMockMurengUser
        public void 질문_목록_최신순_페이징_조회_테스트() throws Exception {
            mockMvc.perform(
                    get("/api/questions?page=0&size=2&sort=newest")
            ).andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].questionId").value(11))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].questionId").value(10))
                    .andDo(print());
        }
    }

    @Test
    @WithMockMurengUser
    public void 질문_아이디_조회_테스트() throws Exception {
        mockMvc.perform(
                get("/api/questions/{questionId}", QUESTION_ID)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questionId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("This is test question."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.koContent").value("이것은 테스트 질문입니다."))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 내가_만든_질문_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                get("/api/questions/me")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("This is user-defined question."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].repliesCount").value(1))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 질문_등록_테스트() throws Exception {
        String questionJsonString = "{\"content\": \"This is english content.\", " +
                "\"koContent\": \"이것은 한글 내용입니다.\" }";
        mockMvc.perform(
                post("/api/questions")
                        .content(questionJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("This is english content."))
                .andDo(print());
    }
}
