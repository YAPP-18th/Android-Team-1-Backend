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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReplyLikesControllerTest extends AbstractControllerTest {
    @MockBean
    private ReplyLikesService replyLikesService;

    private static final long REPLY_ID = 1L;

    @Test
    @WithMockMurengUser
    public void 답변_좋아요_등록_테스트() throws Exception {
        given(replyLikesService.postReplyLikes(any(), eq(REPLY_ID))).willReturn(EntityCreator.createReplyLikesEntity());

        mockMvc.perform(
                post("/api/reply/{replyId}/reply-likes", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.likes").value(true))
                .andDo(print());
    }
}
