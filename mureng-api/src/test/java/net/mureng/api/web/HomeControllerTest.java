package net.mureng.api.web;

import net.mureng.api.core.dto.UserDetailsImpl;
import net.mureng.api.core.jwt.component.JwtCreator;
import net.mureng.core.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class HomeControllerTest extends AbstractControllerTest {

    @Autowired
    private JwtCreator jwtCreator;

    @Test
    public void 테스트_GET_비인가사용자() throws Exception {
        mockMvc.perform(
                get("/api/authenticated-test")
        ).andExpect(status().isUnauthorized());
        // mockMvc 레벨 테스트에서는 필터에 걸러지면 후처리는 안하기 때문에 응답 바디 데이터 검증이 불가능하다.
        // 즉, /error 엔드포인트까지 도달하지 않는다.
    }

    @Test
    public void 테스트_GET_인가사용자() throws Exception {
        String identifier = "identity";

        Member member = Member.builder()
                            .memberId(1L)
                            .identifier(identifier)
                            .email("test@gmail.com")
                            .isActive(true)
                            .nickname("테스트유저")
                            .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                            .modDate(LocalDateTime.of(2020, 10, 14, 17, 11, 10))
                            .build();

        String token = jwtCreator.createAccessToken(member);

        mockMvc.perform(
                    get("/api/authenticated-test")
                    .header("X-AUTH-TOKEN", token)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"));
    }

}