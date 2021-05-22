package net.mureng.core.badge.repository;

import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.entity.BadgeAccomplishedPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeAccomplishedRepository extends JpaRepository<BadgeAccomplished, BadgeAccomplishedPK> {
    List<BadgeAccomplished> findBadgeAccomplishedsByIdMemberId(Long memberId);
}
