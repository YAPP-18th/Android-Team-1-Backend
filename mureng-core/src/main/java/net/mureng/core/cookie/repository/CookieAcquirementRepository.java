package net.mureng.core.cookie.repository;

import net.mureng.core.cookie.entity.CookieAcquirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CookieAcquirementRepository extends JpaRepository<CookieAcquirement, Long> {
    boolean existsByRegDateAndMemberMemberId(LocalDate date, Long memberId);
}
