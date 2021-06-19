package net.mureng.api.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.core.member.service.FcmTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FcmTokenControllerTest extends AbstractControllerTest {
    @MockBean
    private FcmTokenService tokenService;

    @Test
    public void 비로그인_사용자_fcm토큰호출_테스트() throws Exception {
        mockMvc.perform(
                post("/api/fcm-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fcmToken\": \"testToken\"}")
        ).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"));
    }

    @Test
    @WithMockMurengUser
    public void 로그인_사용자_fcm토큰호출_테스트() throws Exception {
        mockMvc.perform(
                put("/api/member/me/fcm-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fcmToken\": \"testToken\"}")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"));
    }
}