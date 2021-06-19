package net.mureng.core.member.repository;

import net.mureng.core.member.entity.FcmToken;
import net.mureng.core.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByMember(Member member);
}
