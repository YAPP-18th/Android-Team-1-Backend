package net.mureng.api.reply.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.web.AbstractControllerTest;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.reply.service.ReplyLikesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReplyLikesControllerTest extends AbstractControllerTest {

    @Test
    @WithMockMurengUser
    public void 답변_좋아요_등록_테스트() throws Exception {
        mockMvc.perform(
                post("/api/reply/{replyId}/reply-likes", 2)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replyId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.likes").value(true))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_이미넣은_좋아요_등록_오류_테스트() throws Exception {
        mockMvc.perform(
                post("/api/reply/{replyId}/reply-likes", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_좋아요_삭제_테스트() throws Exception {
        mockMvc.perform(
                delete("/api/reply/{replyId}/reply-likes", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.deleted").value(true))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_존재하지않는_좋아요_삭제_오류_테스트() throws Exception {
        mockMvc.perform(
                delete("/api/reply/{replyId}/reply-likes", 2)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isInternalServerError())
                .andDo(print());
    }
}
