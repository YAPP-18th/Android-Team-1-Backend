package net.mureng.mureng.domain.badge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeAccomplishedRepository extends JpaRepository<BadgeAccomplished, BadgeAccomplishedPK> {
    List<BadgeAccomplished> findBadgeAccomplishedsByIdMemberId(Long memberId);
}
