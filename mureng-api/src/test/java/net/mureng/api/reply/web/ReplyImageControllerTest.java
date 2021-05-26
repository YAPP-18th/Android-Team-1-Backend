package net.mureng.api.reply.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.reply.service.ReplyImageService;
import net.mureng.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReplyImageControllerTest extends AbstractControllerTest {

    @MockBean
    private ReplyImageService replyImageService;

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

    @Test
    @WithMockMurengUser
    public void 기본이미지_조회_테스트() throws Exception {
        given(replyImageService.getReplyDefaultImageList())
                .willReturn(List.of("/image/default/1", "/image/default/2"));

        mockMvc.perform(
                get("/api/reply/default-images")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").value("/image/default/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1]").value("/image/default/2"))
                .andDo(print());
    }
}