package net.mureng.api.reply.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.core.dto.ApiPageRequest;
import net.mureng.api.reply.service.ReplyImageService;
import net.mureng.api.reply.service.ReplyPaginationService;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.service.ReplyService;
import net.mureng.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReplyControllerTest extends AbstractControllerTest {

    @MockBean
    private ReplyService replyService;

    @MockBean
    private ReplyPaginationService replyPaginationService;

    @MockBean
    private ReplyImageService replyImageService;

    private final String newReplyJsonString = "{\"content\": \"Test Reply\",\n" +
            "  \"image\": \"image-path\" ,\n" +
            "  \"questionId\" : 1 }";

    private final String updateReplyJsonString = "{\"content\": \"Test Reply\",\n" +
            "  \"image\": \"image-path\" }";

    private static final Long MEMBER_ID = 1L;
    private static final Long QUESTION_ID = 1L;
    private static final Long REPLY_ID = 1L;

    @Test
    @WithMockMurengUser
    public void 답변_조회_테스트() throws Exception {
        Reply reply1 = EntityCreator.createReplyEntity();
        reply1.setContent("content1");
        Reply reply2 = EntityCreator.createReplyEntity();
        reply2.setContent("content2");
        reply2.setReplyLikes(new HashSet<>());
        List<Reply> replies = Arrays.asList(reply1, reply2);
        int page = 0;
        int size = 10;

        given(replyPaginationService.findReplies(eq(new ApiPageRequest(page, size, ApiPageRequest.PageSort.POPULAR))))
                .willReturn(new PageImpl<>(replies, PageRequest.of(page, size), 2));

        mockMvc.perform(
                get("/api/reply")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("content1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].replyLikeCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].question.content").value("This is english content."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].author.nickname").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("content2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].replyLikeCount").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].question.content").value("This is english content."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].author.nickname").value("Test"))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_상세_조회_테스트() throws Exception {
        given(replyService.findById(eq(REPLY_ID))).willReturn(EntityCreator.createReplyEntity());

        mockMvc.perform(
                get("/api/reply/{replyId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("Test Reply"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replyLikeCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.question.content").value("This is english content."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.author.nickname").value("Test"))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 답변_등록_테스트() throws Exception {
        given(replyService.create(any())).willReturn(EntityCreator.createReplyEntity());

        mockMvc.perform(
                post("/api/reply")
                .content(newReplyJsonString)
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
    public void 답변_수정_테스트() throws Exception {
        given(replyService.update(any())).willReturn(EntityCreator.createReplyEntity());

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
    public void 이미지_업로드_테스트() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "image",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );


        given(replyImageService.uploadReplyImageFile(any())).willReturn("/image/save/path/reply");

        mockMvc.perform(
                multipart("/api/reply/image")
                        .file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.imagePath").value("/image/save/path/reply"))
                .andDo(print());
    }
}
