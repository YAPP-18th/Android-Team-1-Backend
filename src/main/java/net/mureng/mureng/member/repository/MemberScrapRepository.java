package net.mureng.mureng.member.repository;

import net.mureng.mureng.member.entity.MemberScrap;
import net.mureng.mureng.member.entity.MemberScrapPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberScrapRepository extends JpaRepository<MemberScrap, MemberScrapPK> {
    List<MemberScrap> findAllByIdMemberId(Long memberId);
}
