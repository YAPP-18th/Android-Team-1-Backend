package net.mureng.api.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.entity.BadgeAccomplishedPK;
import net.mureng.core.badge.repository.BadgeAccomplishedRepository;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.core.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static net.mureng.core.core.message.ErrorMessage.NOT_ACCOMPLISHED_BADGE;

@RequiredArgsConstructor
@Service
public class MemberBadgeService {
    private final BadgeAccomplishedRepository badgeAccomplishedRepository;

    @Transactional(readOnly = true)
    public List<BadgeAccomplished> getMemberBadges(Long memberId){
        return badgeAccomplishedRepository.findBadgeAccomplishedsByIdMemberId(memberId);
    }

    @Transactional
    public boolean updateBadgeAccomplishedChecked(Long memberId, Long badgeId){
        BadgeAccomplishedPK pk = BadgeAccomplishedPK.builder().memberId(memberId).badgeId(badgeId).build();
        BadgeAccomplished badgeAccomplished = badgeAccomplishedRepository.findById(pk).orElseThrow(() -> new BadRequestException(NOT_ACCOMPLISHED_BADGE));
        badgeAccomplished.setIsChecked(true);

        badgeAccomplishedRepository.saveAndFlush(badgeAccomplished);

        return badgeAccomplished.getIsChecked();
    }

    @Transactional(readOnly = true)
    public boolean checkIsBadgeAccomplished(Long memberId, Long badgeId){
        BadgeAccomplishedPK pk = BadgeAccomplishedPK.builder().memberId(memberId).badgeId(badgeId).build();
        Optional<BadgeAccomplished> badgeAccomplished = badgeAccomplishedRepository.findById(pk);

        if(badgeAccomplished.isEmpty()) return false;

        return true;
    }
}
