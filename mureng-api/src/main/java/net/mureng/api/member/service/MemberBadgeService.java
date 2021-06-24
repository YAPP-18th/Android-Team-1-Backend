package net.mureng.api.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.entity.BadgeAccomplishedPK;
import net.mureng.core.badge.repository.BadgeAccomplishedRepository;
import net.mureng.core.core.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberBadgeService {
    private final BadgeAccomplishedRepository badgeAccomplishedRepository;

    @Transactional(readOnly = true)
    public List<BadgeAccomplished> getMemberBadges(Long memberId){
        return badgeAccomplishedRepository.findBadgeAccomplishedsByIdMemberId(memberId);
    }

    @Transactional
    public boolean updateBadgeAccomplished(Long memberId, Long badgeId){
        BadgeAccomplishedPK pk = BadgeAccomplishedPK.builder().memberId(memberId).badgeId(badgeId).build();
        BadgeAccomplished badgeAccomplished = badgeAccomplishedRepository.findById(pk).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 리소스에 대한 요청입니다."));
        badgeAccomplished.setIsChecked(true);

        badgeAccomplishedRepository.saveAndFlush(badgeAccomplished);

        return badgeAccomplished.getIsChecked();
    }
}
