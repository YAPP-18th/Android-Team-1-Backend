package net.mureng.core.cookie.repository;

import net.mureng.core.cookie.entity.CookieAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookieAchievementRepository extends JpaRepository<CookieAchievement, Long> {
    Long countByMemberMemberId(Long memberId);
}
