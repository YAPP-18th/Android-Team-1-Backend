package net.mureng.api.reply.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.core.dto.ApiPageRequest;
import net.mureng.api.reply.service.ReplyPaginationService;
import net.mureng.core.badge.service.BadgeAccomplishedService;
import net.mureng.core.badge.service.BadgeAccomplishedServiceImpl;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.service.ReplyService;
import net.mureng.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReplyControllerTest extends AbstractControllerTest {
    private static final Long REPLY_ID = 1L;

    @Test
    @WithMockMurengUser
    public void 답변_인기순_조회_테스트() throws Exception {
        int page = 0;
        int size = 2;
        mockMvc.perform(
                get("/api/reply?page={page}&size={size}&sort=popular", page, size)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].replyId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("this is test reply 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].replyLikeCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].question.content").value("This is test question."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].author.nickname").value("테스트유저"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].replyId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("this is test reply 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].replyLikeCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].question.content").value("This is test question."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].author.nickname").value("테스트유저2"))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_최신순_조회_테스트() throws Exception {
        int page = 0;
        int size = 2;
        mockMvc.perform(
                get("/api/reply?page={page}&size={size}&sort=newest", page, size)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].replyId").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("this is test reply 6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].replyLikeCount").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].question.content").value("This is user-defined question."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].author.nickname").value("테스트유저3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].replyId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("this is test reply 5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].replyLikeCount").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].question.content").value("This is test question."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].author.nickname").value("테스트유저3"))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_상세_조회_테스트() throws Exception {
        mockMvc.perform(
                get("/api/reply/{replyId}", REPLY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("this is test reply 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replyLikeCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.question.content").value("This is test question."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.author.nickname").value("테스트유저"))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_등록_테스트() throws Exception {
        String newReplyJsonString = "{\"content\": \"Test Reply\",\n" +
                "  \"image\": \"image-path\" ,\n" +
                "  \"questionId\" : 3 }";
        mockMvc.perform(
                post("/api/reply")
                .content(newReplyJsonString)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("Test Reply"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.image").value("image-path"))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 이미_등록한_답변_오류_테스트() throws Exception {
        String alreadyRepliedQuestionJsonString = "{\"content\": \"Test Reply\",\n" +
                "  \"image\": \"image-path\" ,\n" +
                "  \"questionId\" : 1 }";
        mockMvc.perform(
                post("/api/reply")
                        .content(alreadyRepliedQuestionJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_수정_테스트() throws Exception {
        String updateReplyJsonString = "{\"content\": \"Test Reply\",\n" +
                "  \"image\": \"image-path\" }";
        mockMvc.perform(
                put("/api/reply/1")
                        .content(updateReplyJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("Test Reply"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.image").value("image-path"))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_삭제_테스트() throws Exception {
        mockMvc.perform(
                delete("/api/reply/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.deleted").value(true))
                .andDo(print());
    }
}
