package net.mureng.core.cookie.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.cookie.entity.CookieAchievement;
import net.mureng.core.cookie.repository.CookieAchievementRepository;
import net.mureng.core.member.entity.Member;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CookieAchievementService {
    private final CookieAchievementRepository cookieRepository;

    @Transactional
    public void achievementMurengCookie(Member member){
        CookieAchievement cookie = CookieAchievement.builder().member(member).build();

        cookieRepository.saveAndFlush(cookie);
    }
}
