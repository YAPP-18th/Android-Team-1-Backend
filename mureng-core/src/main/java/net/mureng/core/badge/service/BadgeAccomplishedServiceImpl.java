package net.mureng.core.badge.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.entity.BadgeAccomplishedPK;
import net.mureng.core.badge.repository.BadgeAccomplishedRepository;
import net.mureng.core.member.repository.MemberScrapRepository;
import net.mureng.core.member.service.MemberService;
import net.mureng.core.reply.repository.ReplyRepository;
import net.mureng.core.reply.service.ReplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BadgeAccomplishedServiceImpl implements BadgeAccomplishedService {
    private final ReplyRepository replyRepository;
    private final MemberService memberService;
    private final BadgeService badgeService;
    private final BadgeAccomplishedRepository badgeAccomplishedRepository;
    private final MemberScrapRepository memberScrapRepository;


    @Transactional
    public boolean createMureng3Days(Long memberId) {
        if ( replyRepository.findAllByAuthorMemberId(memberId).size() == Mureng3Days.conditionOfCount &&
                !badgeAccomplishedRepository.existsBadgeAccomplishedByMemberMemberIdAndBadgeBadgeId(memberId, Mureng3Days.id) ){

            badgeAccomplishedRepository.saveAndFlush(makeBadgeAccomplished(memberId, Mureng3Days.id));
            return true;
        }

        return false;
    }

    @Transactional
    public boolean createCelebrityMureng(Long memberId) {

        return false;
    }

    @Transactional
    public boolean createAcademicMureng(Long memberId) {
        if( memberScrapRepository.findAllByIdMemberId(memberId).size() == AcademicMureng.conditionOfCount &&
                !badgeAccomplishedRepository.existsBadgeAccomplishedByMemberMemberIdAndBadgeBadgeId(memberId, AcademicMureng.id ) ){

            badgeAccomplishedRepository.saveAndFlush(makeBadgeAccomplished(memberId, AcademicMureng.id));
            return true;
        }

        return false;
    }

    private BadgeAccomplished makeBadgeAccomplished(Long memberId, Long badgeId){
        BadgeAccomplishedPK badgeAccomplishedPK = BadgeAccomplishedPK.builder()
                .memberId(memberId)
                .badgeId(badgeId)
                .build();

        BadgeAccomplished badgeAccomplished = BadgeAccomplished.builder()
                .id(badgeAccomplishedPK)
                .member(memberService.findById(memberId))
                .badge(badgeService.findById(badgeId))
                .build();

        return badgeAccomplished;
    }
    
    private static class Mureng3Days{
        private final static Long id = 1L;
        private final static int conditionOfCount = 3;
    }

    private static class AcademicMureng {
        private final static Long id = 3L;
        private final static int conditionOfCount = 3;
    }
}
