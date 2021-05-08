package net.mureng.mureng.jwt;

import net.mureng.mureng.core.jwt.component.JwtCreator;
import net.mureng.mureng.core.jwt.component.JwtValidator;
import net.mureng.mureng.core.jwt.dto.TokenDto;
import net.mureng.mureng.core.jwt.service.JwtService;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.repository.MemberRepository;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class JwtAuthenticationTest extends AbstractControllerTest {
    @Autowired
    JwtCreator jwtCreator;

    @Autowired
    JwtValidator jwtValidator;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private MemberRepository memberRepository;

    private final String emailJsonString = "{\"email\": \"test@gmail.com\"}";

    @Test
    public void Jwt_생성(){
        String email = "test@gmail.com";
        String nickname = "Test";
        String token = jwtCreator.createToken(email, nickname);

        assertTrue(jwtValidator.validateToken(token));
    }

    @Test
    public void JWT발급() throws Exception {
        Member member = Member.builder()
                .memberId(1L)
                .identifier("123")
                .email("test@gmail.com")
                .isActive(true)
                .nickname("Test")
                .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                .modDate(LocalDateTime.of(2020, 10, 14, 17, 11, 10))
                .murengCount(0)
                .build();

        given(jwtService.issue(eq("test@gmail.com"))).willReturn(new TokenDto("testToken", null));

        mockMvc.perform(
                post("/api/jwt")
                        .content(emailJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.accessToken").value("testToken"))
                .andDo(print());
    }
}
