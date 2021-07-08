package net.mureng.core.badge.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.entity.BadgeAccomplishedPK;
import net.mureng.core.badge.repository.BadgeAccomplishedRepository;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.member.repository.MemberRepository;
import net.mureng.core.member.repository.MemberScrapRepository;
import net.mureng.core.member.service.MemberService;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static net.mureng.core.core.message.ErrorMessage.NOT_EXIST_REPLY;

@RequiredArgsConstructor
@Service
public class BadgeAccomplishedServiceImpl implements BadgeAccomplishedService {
    private final ReplyRepository replyRepository;
    private final MemberService memberService;
    private final BadgeService badgeService;
    private final BadgeAccomplishedRepository badgeAccomplishedRepository;
    private final MemberScrapRepository memberScrapRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public boolean createMureng3Days(Long memberId) {
        if ( memberRepository.findById(memberId).get().getMurengCount() == Mureng3Days.conditionOfCount &&
                !badgeAccomplishedRepository.existsBadgeAccomplishedByMemberMemberIdAndBadgeBadgeId(memberId, Mureng3Days.id) ){

            badgeAccomplishedRepository.saveAndFlush(makeBadgeAccomplished(memberId, Mureng3Days.id));
            return true;
        }

        return false;
    }

    @Transactional
    public boolean createCelebrityMureng(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new BadRequestException(NOT_EXIST_REPLY));
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
        if ( memberRepository.findById(memberId).get().getMurengCount() == MurengSet.conditionOfCount &&
                !badgeAccomplishedRepository.existsBadgeAccomplishedByMemberMemberIdAndBadgeBadgeId(memberId, MurengSet.id) ){

            badgeAccomplishedRepository.saveAndFlush(makeBadgeAccomplished(memberId, MurengSet.id));
            return true;
        }

        return false;
    }

    /**
     * 뱃지를 획득하지 않았거나 혹은 획득했는데 이미 확인한 경우
     */
    @Transactional(readOnly = true)
    public boolean isAlreadyCheckedCelebrityMureng(Long memberId){
        BadgeAccomplishedPK pk = BadgeAccomplishedPK.builder().memberId(memberId).badgeId(CelebrityMureng.id).build();
        Optional<BadgeAccomplished> badgeAccomplished = badgeAccomplishedRepository.findById(pk);

        if(badgeAccomplished.isEmpty() || badgeAccomplished.get().getIsChecked())
            return false;

        return true;
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
