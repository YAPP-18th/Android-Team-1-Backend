package net.mureng.api.oauth;

import net.mureng.api.core.oauth2.dto.OAuth2Profile;
import net.mureng.api.core.oauth2.service.OAuth2Service;
import net.mureng.api.web.AbstractControllerTest;
import net.mureng.core.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OAuthTest extends AbstractControllerTest {

    private final String accessTokenJson = "{\"accessToken\": \"testToken\"}";

    @MockBean
    private OAuth2Service oAuth2Service;

    @MockBean
    private MemberService memberService;

    @Test
    public void 액세스토큰과_일치하는_사용자체크() throws Exception {

        given(oAuth2Service.getProfile(eq("google"), eq("testToken"))).willReturn(new OAuth2Profile("test@gmail.com"));
        given(memberService.isEmailExist(eq("test@gmail.com"))).willReturn(true);

        mockMvc.perform(
                post("/api/member/user-exists/google")
                        .content(accessTokenJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.exist").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("test@gmail.com"))
                .andDo(print());
    }
}
