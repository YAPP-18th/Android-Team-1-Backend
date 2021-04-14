package net.mureng.mureng.member.repository;

import net.mureng.mureng.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
