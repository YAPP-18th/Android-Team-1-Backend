package net.mureng.api.member.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.member.service.MemberImageService;
import net.mureng.api.reply.service.ReplyImageService;
import net.mureng.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberImageControllerTest extends AbstractControllerTest {
    @MockBean
    private MemberImageService memberImageService;

    @Test
    @WithMockMurengUser
    public void 이미지_업로드_테스트() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "image",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        given(memberImageService.uploadMemberImageFile(any())).willReturn("/image/save/path/reply");

        mockMvc.perform(
                multipart("/api/member/image")
                        .file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.imagePath").value("/image/save/path/reply"))
                .andDo(print());
    }
}