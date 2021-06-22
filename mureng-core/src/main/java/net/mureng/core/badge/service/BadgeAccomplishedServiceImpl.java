package net.mureng.core.badge.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.entity.BadgeAccomplishedPK;
import net.mureng.core.badge.repository.BadgeAccomplishedRepository;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.member.repository.MemberScrapRepository;
import net.mureng.core.member.service.MemberService;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.repository.ReplyRepository;
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
        if ( replyRepository.countAllByAuthorMemberId(memberId) == Mureng3Days.conditionOfCount &&
                !badgeAccomplishedRepository.existsBadgeAccomplishedByMemberMemberIdAndBadgeBadgeId(memberId, Mureng3Days.id) ){

            badgeAccomplishedRepository.saveAndFlush(makeBadgeAccomplished(memberId, Mureng3Days.id));
            return true;
        }

        return false;
    }

    @Transactional
    public boolean createCelebrityMureng(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new BadRequestException("존재하지 않는 답변에 대한 요청입니다."));
        Long memberId = reply.getAuthor().getMemberId();

        if(reply.getReplyLikes().size() == CelebrityMureng.conditionOfCount &&
                !badgeAccomplishedRepository.existsBadgeAccomplishedByMemberMemberIdAndBadgeBadgeId(memberId, CelebrityMureng.id)) {

            badgeAccomplishedRepository.saveAndFlush(makeBadgeAccomplished(memberId, CelebrityMureng.id));
            return true;
        }

        return false;
    }

    @Transactional
    public boolean createAcademicMureng(Long memberId) {
        if( memberScrapRepository.countByMemberMemberId(memberId) == AcademicMureng.conditionOfCount &&
                !badgeAccomplishedRepository.existsBadgeAccomplishedByMemberMemberIdAndBadgeBadgeId(memberId, AcademicMureng.id ) ){

            badgeAccomplishedRepository.saveAndFlush(makeBadgeAccomplished(memberId, AcademicMureng.id));
            return true;
        }

        return false;
    }

    @Transactional
    public boolean createMurengSet(Long memberId){
        if ( replyRepository.countAllByAuthorMemberId(memberId) == MurengSet.conditionOfCount &&
                !badgeAccomplishedRepository.existsBadgeAccomplishedByMemberMemberIdAndBadgeBadgeId(memberId, MurengSet.id) ){

            badgeAccomplishedRepository.saveAndFlush(makeBadgeAccomplished(memberId, MurengSet.id));
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


    public static class Mureng3Days{
        public final static Long id = 1L;
        public final static int conditionOfCount = 3;
    }

    public static class CelebrityMureng {
        public final static Long id = 2L;
        public final static int conditionOfCount = 10;
    }

    public static class AcademicMureng {
        public final static Long id = 3L;
        public final static int conditionOfCount = 3;
    }

    public static class MurengSet{
        public final static Long id = 4L;
        public final static int conditionOfCount = 30;
    }
}
