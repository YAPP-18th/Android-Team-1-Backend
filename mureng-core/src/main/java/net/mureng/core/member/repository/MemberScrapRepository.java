package net.mureng.core.member.repository;

import net.mureng.core.member.entity.MemberScrap;
import net.mureng.core.member.entity.MemberScrapPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberScrapRepository extends JpaRepository<MemberScrap, MemberScrapPK> {
    List<MemberScrap> findAllByIdMemberId(Long memberId);
}
