package net.mureng.mureng.jwt;

import net.mureng.mureng.core.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtAuthenticationTest {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void JwtToken_생성(){
        String email = "test@gmail.com";
        String nickname = "Test";
        String token = jwtTokenProvider.createToken(email, nickname);

        assertTrue(jwtTokenProvider.validateToken(token));
    }
}
