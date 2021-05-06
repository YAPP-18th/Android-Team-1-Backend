package net.mureng.mureng.question.web;

import net.mureng.mureng.annotation.WithMockMurengUser;
import net.mureng.mureng.core.dto.UserDetailsImpl;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.repository.MemberRepository;
import net.mureng.mureng.member.service.MemberService;
import net.mureng.mureng.member.service.UserDetailsServiceImpl;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.service.TodayQuestionService;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TodayQuestionControllerTest extends AbstractControllerTest {
    @MockBean
    private TodayQuestionService todayQuestionService;

    private final Question question = Question.builder()
            .questionId(1L)
            .member(Member.builder().build())
            .category("카테고리")
            .content("This is english content.")
            .koContent("이것은 한글 내용입니다.")
            .regDate(LocalDateTime.parse("2020-10-14T11:00:00"))
            .build();

    @Test
    @WithMockMurengUser
    public void 오늘의_질문_조회_테스트() throws Exception {
        given(todayQuestionService.getTodayQuestionByMemberId(eq(1L))).willReturn(question);

        mockMvc.perform(
                get("/api/today-question")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questionId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.category").value("카테고리"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("This is english content."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.koContent").value("이것은 한글 내용입니다."));
    }
}