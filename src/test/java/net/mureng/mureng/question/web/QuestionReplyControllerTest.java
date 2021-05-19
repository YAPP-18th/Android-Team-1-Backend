package net.mureng.mureng.question.web;

import net.mureng.mureng.annotation.WithMockMurengUser;
import net.mureng.mureng.common.EntityCreator;
import net.mureng.mureng.core.dto.ApiPageRequest;
import net.mureng.mureng.core.exception.ResourceNotFoundException;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.service.ReplyService;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionReplyControllerTest extends AbstractControllerTest {
    @MockBean
    private ReplyService replyService;

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

        given(replyService.findRepliesByQuestionId(eq(1L), eq(new ApiPageRequest(page, size, ApiPageRequest.PageSort.POPULAR))))
                .willReturn(new PageImpl<>(replies, PageRequest.of(page, size), 2));

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

    @Test
    @WithMockMurengUser
    public void 질문_내답변_가져오기_테스트() throws Exception {
        Reply reply = EntityCreator.createReplyEntity();
        reply.setContent("content1");

        given(replyService.findReplyByQuestionIdAndMember(eq(1L), eq(1L))).willReturn(reply);

        mockMvc.perform(
                get("/api/questions/1/replies/me")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("content1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replyLikeCount").value(2))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 질문_내답변_가져오기_404_테스트() throws Exception {
        given(replyService.findReplyByQuestionIdAndMember(eq(1L), eq(1L))).willThrow(ResourceNotFoundException.class);

        mockMvc.perform(
                get("/api/questions/1/replies/me")
        ).andExpect(status().isNotFound())
                .andDo(print());
    }
}