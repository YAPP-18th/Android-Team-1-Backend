package net.mureng.api.core.jwt.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.api.core.jwt.component.JwtCreator;
import net.mureng.api.core.jwt.dto.TokenDto;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtCreator jwtCreator;
    private final MemberRepository memberRepository;

    @Transactional
    public TokenDto issue(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("해당 유저 정보가 없습니다."));

        TokenDto token = new TokenDto();
        token.setAccessToken(jwtCreator.createToken(member.getEmail(), member.getNickname()));

        return token;
    }
}
