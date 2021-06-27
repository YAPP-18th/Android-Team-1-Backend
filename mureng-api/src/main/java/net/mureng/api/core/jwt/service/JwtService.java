package net.mureng.api.core.jwt.service;

import lombok.RequiredArgsConstructor;
import net.mureng.api.core.jwt.component.JwtCreator;
import net.mureng.api.core.jwt.component.JwtResolver;
import net.mureng.api.core.jwt.component.JwtValidator;
import net.mureng.api.core.jwt.dto.TokenDto;
import net.mureng.api.core.oauth2.service.OAuth2Service;
import net.mureng.core.core.component.DateFactory;
import net.mureng.core.core.exception.UnauthorizedException;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtCreator jwtCreator;
    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;
    private final OAuth2Service oAuth2Service;
    private final MemberService memberService;
    private final DateFactory dateFactory;

    @Deprecated
    @Transactional
    public TokenDto.Mureng issue(String identifier){
        Member member = memberService.findByIdentifier(identifier);

        TokenDto.Mureng token = new TokenDto.Mureng();
        token.setMurengAccessToken(jwtCreator.createAccessToken(member));
        token.setMurengRefreshToken(jwtCreator.createRefreshToken(member));
        return token;
    }

    @Transactional
    public TokenDto.Mureng issue(TokenDto.Provider providerToken){
        String identifier = oAuth2Service.getProfile(providerToken).getIdentifier();
        Member member = memberService.findByIdentifier(identifier);

        String accessToken = jwtCreator.createAccessToken(member);
        String refreshToken = jwtCreator.createRefreshToken(member);
        return new TokenDto.Mureng(accessToken, refreshToken);
    }

    @Transactional
    public TokenDto.Mureng refresh(TokenDto.MurengRefresh murengRefreshToken){
        if (isExpired(murengRefreshToken)) {
            throw new UnauthorizedException("Refresh Token이 만료되었습니다. 다시 로그인 해주세요.");
        }

        String identifier = jwtResolver.getUserIdentifier(murengRefreshToken.getMurengRefreshToken());
        Member member = memberService.findByIdentifier(identifier);

        String accessToken = jwtCreator.createAccessToken(member);
        String refreshToken = murengRefreshToken.getMurengRefreshToken();
        if (isRefreshable(murengRefreshToken)) {
            refreshToken = jwtCreator.createRefreshToken(member);
        }

        return new TokenDto.Mureng(accessToken, refreshToken);
    }

    private boolean isExpired(TokenDto.MurengRefresh murengRefreshToken) {
        return ! jwtValidator.validateToken(murengRefreshToken.getMurengRefreshToken());
    }

    private boolean isRefreshable(TokenDto.MurengRefresh murengRefreshToken) {
        // 유효기간이 1달 이내로 남았다면 리프레시 대상
        Date datePlusOneMonth = dateFactory.addFromNow(Calendar.MONTH, 1);
        return ! jwtValidator.validateTokenBefore(murengRefreshToken.getMurengRefreshToken(), datePlusOneMonth);
    }
}
