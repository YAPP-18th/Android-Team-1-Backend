package net.mureng.mureng.reply.web;

import net.mureng.mureng.annotation.WithMockMurengUser;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.entity.WordHint;
import net.mureng.mureng.question.service.QuestionService;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.service.ReplyService;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReplyControllerTest extends AbstractControllerTest {
    @MockBean
    private QuestionService questionService;

    @MockBean
    private ReplyService replyService;

    private final Question question = Question.builder()
            .questionId(1L)
            .member(Member.builder().build())
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

    private final Reply newReply = Reply.builder()
            .replyId(1L)
            .member(Member.builder().build())
            .question(question)
            .content("Test Reply")
            .image("image-path")
            .build();

    private final String newReplyJsonString = "{\"content\": \"Test Reply\",\n" +
            "  \"image\": \"image-path\" }";

    @Test
    @WithMockMurengUser
    public void 답변_등록_테스트() throws Exception {
        given(questionService.getQuestionById(eq(1L))).willReturn(question);
        given(replyService.postReply(any())).willReturn(newReply);

        mockMvc.perform(
                post("/api/reply/1")
                .content(newReplyJsonString)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("Test Reply"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.image").value("image-path"))
                .andDo(print());
    }
}
