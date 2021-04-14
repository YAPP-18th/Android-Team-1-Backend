package net.mureng.mureng.badge.repository;

import net.mureng.mureng.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
