package net.mureng.api.question.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.core.question.service.TodayQuestionSelectionService;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.entity.Question;
import net.mureng.core.question.entity.WordHint;
import net.mureng.api.web.AbstractControllerTest;
import net.mureng.core.question.service.TodayQuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TodayQuestionControllerTest extends AbstractControllerTest {
    @MockBean
    private TodayQuestionSelectionService todayQuestionSelectionService;

    @Test
    @WithMockMurengUser
    public void 오늘의_질문_조회_테스트() throws Exception {
        mockMvc.perform(
                get("/api/today-question")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questionId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.category").value("테스트"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("This is test question."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.koContent").value("이것은 테스트 질문입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.wordHints[0].hintId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.wordHints[0].word").value("meaning 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.wordHints[0].meaning").value("의미 1"));
    }

    @Test
    @WithMockMurengUser
    public void 오늘의_질문_새로고침_테스트() throws Exception {
        Question question = Question.builder()
                .questionId(1L)
                .author(Member.builder().murengCookies(Set.of()).build())
                .category("카테고리")
                .content("This is english content.")
                .koContent("이것은 한글 내용입니다.")
                .regDate(LocalDateTime.parse("2020-10-14T11:00:00"))
                .wordHints(new HashSet<>(List.of(
                        WordHint.builder()
                                .hintId(1L)
                                .question(Question.builder().build())
                                .word("apple")
                                .meaning("사과")
                                .regDate(LocalDateTime.parse("2020-10-14T11:00:00"))
                                .build()
                )))
                .build();
        given(todayQuestionSelectionService.reselectTodayQuestion(any())).willReturn(question);

        mockMvc.perform(
                get("/api/today-question/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questionId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.category").value("카테고리"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("This is english content."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.koContent").value("이것은 한글 내용입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.wordHints[0].hintId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.wordHints[0].word").value("apple"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.wordHints[0].meaning").value("사과"));
    }
}