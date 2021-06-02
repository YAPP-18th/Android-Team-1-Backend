package net.mureng.api.core.jwt.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtCreator {
    private final long TOKEN_VALID_MILISECOND = 1000l * 60 * 60 * 24; // 24 시간

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String identifier, String nickname) {
        Claims claims = Jwts.claims().setSubject(identifier); // claim 임의로 설정
        claims.put("nickname", nickname);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_MILISECOND))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

}
