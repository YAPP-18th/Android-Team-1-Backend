package net.mureng.core.badge.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.entity.BadgeAccomplishedPK;
import net.mureng.core.badge.repository.BadgeAccomplishedRepository;
import net.mureng.core.member.service.MemberService;
import net.mureng.core.reply.service.ReplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BadgeAccomplishedServiceImpl implements BadgeAccomplishedService {
    private final ReplyService replyService;
    private final MemberService memberService;
    private final BadgeService badgeService;
    private final BadgeAccomplishedRepository badgeAccomplishedRepository;

    private final static Long MURENG_3DAYS = 1L;

    @Transactional
    public boolean createMureng3Days(Long memberId) {
        if ( replyService.findRepliesByMemberId(memberId).size() == 3 &&
                !badgeAccomplishedRepository.existsBadgeAccomplishedByBadgeBadgeIdAndMemberMemberId(MURENG_3DAYS, memberId) ){

            BadgeAccomplishedPK badgeAccomplishedPK = BadgeAccomplishedPK.builder()
                    .badgeId(MURENG_3DAYS)
                    .memberId(memberId)
                    .build();

            BadgeAccomplished badgeAccomplished = BadgeAccomplished.builder()
                    .id(badgeAccomplishedPK)
                    .member(memberService.findById(memberId))
                    .badge(badgeService.findById(MURENG_3DAYS))
                    .build();

            badgeAccomplishedRepository.saveAndFlush(badgeAccomplished);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean createCelebrityMureng() {
        return false;
    }

    @Transactional

    public boolean createAcademicMureng() {
        return false;
    }
}
