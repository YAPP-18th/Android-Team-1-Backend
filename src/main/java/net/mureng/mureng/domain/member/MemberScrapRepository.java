package net.mureng.mureng.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberScrapRepository extends JpaRepository<MemberScrap, MemberScrapPK> {
    List<MemberScrap> findAllByIdMemberId(Long memberId);
}
