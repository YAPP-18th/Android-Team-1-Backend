package net.mureng.core.cookie.repository;

import net.mureng.core.cookie.entity.CookieAcquirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookieAcquirementRepository extends JpaRepository<CookieAcquirement, Long> {
    Long countByMemberMemberId(Long memberId);
}
