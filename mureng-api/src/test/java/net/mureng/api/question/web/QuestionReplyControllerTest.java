package net.mureng.api.question.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.core.dto.ApiPageRequest;
import net.mureng.api.reply.service.ReplyPaginationService;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.core.exception.ResourceNotFoundException;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.service.ReplyService;
import net.mureng.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionReplyControllerTest extends AbstractControllerTest {
    @Test
    @WithMockMurengUser
    public void 질문_관련_답변_목록_가져오기_인기순_테스트() throws Exception {
        mockMvc.perform(
                get("/api/questions/1/replies?page=0&size=2&sort=popular")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("this is test reply 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].replyLikeCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].requestedByAuthor").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("this is test reply 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].replyLikeCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].requestedByAuthor").value(false))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 질문_관련_답변_목록_가져오기_최신순_테스트() throws Exception {
        mockMvc.perform(
                get("/api/questions/1/replies?page=0&size=2&sort=newest")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("this is test reply 5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].replyLikeCount").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].requestedByAuthor").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("this is test reply 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].replyLikeCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].requestedByAuthor").value(false))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 질문_내답변_가져오기_테스트() throws Exception {
        mockMvc.perform(
                get("/api/questions/1/replies/me")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("this is test reply 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replyLikeCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.requestedByAuthor").value(true))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 질문_내답변_가져오기_400_테스트() throws Exception {
        mockMvc.perform(
                get("/api/questions/10/replies/me")
        ).andExpect(status().isBadRequest())
                .andDo(print());
    }
}