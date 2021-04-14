package net.mureng.mureng.badge.repository;

import net.mureng.mureng.badge.entity.BadgeAccomplished;
import net.mureng.mureng.badge.entity.BadgeAccomplishedPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeAccomplishedRepository extends JpaRepository<BadgeAccomplished, BadgeAccomplishedPK> {
    List<BadgeAccomplished> findBadgeAccomplishedsByIdMemberId(Long memberId);
}
