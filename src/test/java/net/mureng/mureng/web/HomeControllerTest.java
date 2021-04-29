package net.mureng.mureng.web;

import net.mureng.mureng.core.dto.CustomUserDetails;
import net.mureng.mureng.core.jwt.JwtTokenProvider;
import net.mureng.mureng.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class HomeControllerTest extends AbstractControllerTest{

    @Autowired
    private HomeController homeController;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserDetailsService userDetailsService;

    @Override
    protected Object controller() {
        return homeController;
    }

    @Test
    public void 테스트_GET_비인가사용자() throws Exception {
        mockMvc.perform(
                get("/api/test")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void 테스트_GET_인가사용자() throws Exception {
        String email = "test@gmail.com";

        Member member = Member.builder()
                            .memberId(1L)
                            .identifier("123")
                            .email(email)
                            .isActive(true)
                            .nickname("Test")
                            .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                            .modDate(LocalDateTime.of(2020, 10, 14, 17, 11, 10))
                            .murengCount(0L)
                            .build();

        String token = jwtTokenProvider.createToken(member.getEmail(), member.getNickname());

        given(userDetailsService.loadUserByUsername(eq(email))).willReturn(new CustomUserDetails(member));

        mockMvc.perform(
                get("/api/test")
                .header("X-AUTH-TOKEN", token)
        );
    }

}