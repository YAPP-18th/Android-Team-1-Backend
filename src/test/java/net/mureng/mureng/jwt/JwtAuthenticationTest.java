package net.mureng.mureng.jwt;

import net.mureng.mureng.core.jwt.JwtCreator;
import net.mureng.mureng.core.jwt.JwtValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtAuthenticationTest {
    @Autowired
    JwtCreator jwtCreator;

    @Autowired
    JwtValidator jwtValidator;

    @Test
    public void JwtToken_생성(){
        String email = "test@gmail.com";
        String nickname = "Test";
        String token = jwtCreator.createToken(email, nickname);

        assertTrue(jwtValidator.validateToken(token));
    }
}
