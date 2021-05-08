package net.mureng.mureng.member.web;

import net.mureng.mureng.core.oauth2.dto.OAuth2Profile;
import net.mureng.mureng.core.oauth2.service.OAuth2Service;
import net.mureng.mureng.member.service.MemberService;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends AbstractControllerTest {
    private final String newMemberJsonString = "{\"attendanceCount\": 0,\n" +
                                                "  \"dailyEndTime\": \"11:30:00\",\n" +
                                                "  \"email\": \"example@email.com\",\n" +
                                                "  \"identifier\": \"user-identifier\",\n" +
                                                "  \"image\": \"image-path\",\n" +
                                                "  \"lastAttendanceDate\": \"2020-10-14\",\n" +
                                                "  \"murengCount\": 0,\n" +
                                                "  \"nickname\": \"최대여섯글자\",\n" +
                                                "  \"pushActive\": true\n" +
                                                "}";

    private final String accessTokenJson = "{\"accessToken\": \"testToken\"}";

    @MockBean
    private OAuth2Service oAuth2Service;

    @MockBean
    private MemberService memberService;

    @Test
    public void 사용자_회원가입_테스트() throws Exception {
        mockMvc.perform(
                post("/api/member/signup")
                .content(newMemberJsonString)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.dailyEndTime").value("11:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("example@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.identifier").value("user-identifier"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.image").value("image-path"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastAttendanceDate").value("2020-10-14"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.murengCount").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickname").value("최대여섯글자"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pushActive").value(true));
    }

    @Test
    public void 사용자_닉네임중복체크_테스트() throws Exception {
        mockMvc.perform(
                get("/api/member/nickname-exists/테스트이름")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.duplicated").value(false));
    }

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