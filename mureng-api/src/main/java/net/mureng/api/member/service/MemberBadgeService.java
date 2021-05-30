package net.mureng.api.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.repository.BadgeAccomplishedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberBadgeService {
    private final BadgeAccomplishedRepository badgeAccomplishedRepository;

    @Transactional(readOnly = true)
    public List<BadgeAccomplished> getMemberBadges(Long memberId){
        return badgeAccomplishedRepository.findBadgeAccomplishedsByIdMemberId(memberId);
    }
}
