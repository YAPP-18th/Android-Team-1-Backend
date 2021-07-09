package net.mureng.core.cookie.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.cookie.entity.CookieAcquirement;
import net.mureng.core.cookie.repository.CookieAcquirementRepository;
import net.mureng.core.member.entity.Member;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CookieAcquirementService {
    private final CookieAcquirementRepository cookieRepository;

    @Transactional
    public void acquireMurengCookie(Member member){
        CookieAcquirement cookie = CookieAcquirement.builder().member(member).build();

        cookieRepository.saveAndFlush(cookie);
    }
}
