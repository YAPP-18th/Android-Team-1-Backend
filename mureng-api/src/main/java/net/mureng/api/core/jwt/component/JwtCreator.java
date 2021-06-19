package net.mureng.api.core.jwt.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import net.mureng.core.core.component.DateFactory;
import net.mureng.core.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtCreator {
    private final long ACCESS_TOKEN_VALID_MILISECOND = 1000 * 60 * 60 * 24; // 24 시간
    private final long REFRESH_TOKEN_VALID_MILISECOND = 1000L * 60 * 60 * 24 * 30 * 2; // 2달

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final DateFactory dateFactory;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(Member member) {
        Claims claims = Jwts.claims().setSubject(member.getIdentifier()); // claim 임의로 설정
        claims.put("nickname", member.getNickname());
        Date now = dateFactory.now();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_MILISECOND))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String createRefreshToken(Member member) {
        Claims claims = Jwts.claims().setSubject(member.getIdentifier()); // claim 임의로 설정
        claims.put("nickname", member.getNickname());
        Date now = dateFactory.now();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_MILISECOND))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
