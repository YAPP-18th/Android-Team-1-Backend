package net.mureng.api.oauth;

import net.mureng.api.core.jwt.component.JwtCreator;
import net.mureng.api.core.jwt.dto.TokenDto;
import net.mureng.api.core.jwt.dto.TokenProvider;
import net.mureng.api.core.jwt.service.JwtService;
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

    private final String providerAccessTokenJson = "{\"providerName\": \"google\", \"providerAccessToken\": \"testToken\"}";

    @MockBean
    private OAuth2Service oAuth2Service;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtService jwtService;

    @Test
    public void 액세스토큰과_일치하는_사용자체크() throws Exception {
        TokenDto.Provider providerToken = new TokenDto.Provider(TokenProvider.GOOGLE, "testToken");
        given(oAuth2Service.getProfile(eq(providerToken))).willReturn(new OAuth2Profile("test"));
        given(memberService.isMemberExist(eq("test"))).willReturn(true);

        mockMvc.perform(
                post("/api/member/user-exists")
                        .content(providerAccessTokenJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.exist").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.identifier").value("test"))
                .andDo(print());
    }

    @Test
    public void 로그인_테스트() throws Exception {
        TokenDto.Provider providerToken = new TokenDto.Provider(TokenProvider.GOOGLE, "testToken");
        TokenDto.Mureng murengToken = new TokenDto.Mureng("testMurengToken", "refreshToken");
        given(jwtService.issue(eq(providerToken))).willReturn(murengToken);

        mockMvc.perform(
                post("/api/member/signin")
                        .content(providerAccessTokenJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.murengAccessToken").value("testMurengToken"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.murengRefreshToken").value("refreshToken"))
                .andDo(print());
    }
}
