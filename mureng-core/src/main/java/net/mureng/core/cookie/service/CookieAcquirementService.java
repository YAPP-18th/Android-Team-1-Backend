package net.mureng.core.cookie.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.cookie.entity.CookieAcquirement;
import net.mureng.core.cookie.repository.CookieAcquirementRepository;
import net.mureng.core.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class CookieAcquirementService {
    private final CookieAcquirementRepository cookieRepository;

    @Transactional
    public void acquireMurengCookie(Member member){
        CookieAcquirement cookie = CookieAcquirement.builder().member(member).build();

        cookieRepository.saveAndFlush(cookie);
    }

    @Transactional(readOnly = true)
    public boolean isAlreadyCookieAcquiredToday(Long memberId) {
        LocalDate date = LocalDate.now();

        return cookieRepository.existsByRegDateAndMemberMemberId(date, memberId);
    }
}
