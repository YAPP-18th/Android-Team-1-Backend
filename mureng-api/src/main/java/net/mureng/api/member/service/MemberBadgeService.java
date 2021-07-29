package net.mureng.api.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.entity.BadgeAccomplishedPK;
import net.mureng.core.badge.repository.BadgeAccomplishedRepository;
import net.mureng.core.core.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static net.mureng.core.core.message.ErrorMessage.MEMBER_CHECK_BADGE_ACCOMPLISHED;
import static net.mureng.core.core.message.ErrorMessage.NOT_ACCOMPLISHED_BADGE;

@Slf4j
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
        BadgeAccomplished badgeAccomplished = badgeAccomplishedRepository.findById(pk).orElseThrow(() -> new EntityNotFoundException(NOT_ACCOMPLISHED_BADGE));
        badgeAccomplished.setIsChecked(true);

        badgeAccomplishedRepository.saveAndFlush(badgeAccomplished);

        return badgeAccomplished.getIsChecked();
    }

    @Transactional(readOnly = true)
    public boolean checkIsBadgeAccomplished(Long memberId, Long badgeId){
        BadgeAccomplishedPK pk = BadgeAccomplishedPK.builder().memberId(memberId).badgeId(badgeId).build();
        Optional<BadgeAccomplished> badgeAccomplished = badgeAccomplishedRepository.findById(pk);

        if(badgeAccomplished.isEmpty() || badgeAccomplished.get().getIsChecked()) {
            log.info(MEMBER_CHECK_BADGE_ACCOMPLISHED);
            return false;
        }

        return true;
    }
}
