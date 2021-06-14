package net.mureng.api.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.api.core.exception.BadRequestException;
import net.mureng.core.badge.service.BadgeAccomplishedService;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.entity.MemberScrap;
import net.mureng.core.member.entity.MemberScrapPK;
import net.mureng.core.member.repository.MemberScrapRepository;
import net.mureng.core.todayexpression.entity.TodayExpression;
import net.mureng.core.todayexpression.repository.TodayExpressionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberExpressionScrapService {

    private final TodayExpressionRepository todayExpressionRepository;
    private final MemberScrapRepository memberScrapRepository;
    private final BadgeAccomplishedService badgeAccomplishedService;

    @Transactional
    public MemberScrap scrapTodayExpression(Member member, Long expId){
        TodayExpression todayExpression = todayExpressionRepository.findById(expId)
                .orElseThrow(() -> new ResolutionException("존재하지 않는 오늘의 표현에 대한 요청입니다."));

        MemberScrapPK memberScrapPK = new MemberScrapPK(member.getMemberId(), todayExpression.getExpId());

        if(memberScrapRepository.existsById(memberScrapPK))
            throw new BadRequestException("이미 스크랩한 표현입니다.");

        MemberScrap memberScrap = MemberScrap.builder().id(memberScrapPK).member(member).todayExpression(todayExpression).build();

        memberScrapRepository.saveAndFlush(memberScrap);

        badgeAccomplishedService.createAcademicMureng(member.getMemberId());

        return memberScrap;
    }

    @Transactional
    public void deleteScrap(Member member, Long expId) {
        TodayExpression todayExpression = todayExpressionRepository.findById(expId)
                .orElseThrow(() -> new ResolutionException("존재하지 않는 오늘의 표현에 대한 요청입니다."));

        MemberScrapPK memberScrapPK = new MemberScrapPK(member.getMemberId(), todayExpression.getExpId());

        if(!memberScrapRepository.existsById(memberScrapPK))
            throw new BadRequestException("이미 스크랩을 취소했습니다.");

        memberScrapRepository.deleteById(memberScrapPK);
    }

    @Transactional(readOnly = true)
    public List<MemberScrap> getMemberScrap(Long memberId){
        return memberScrapRepository.findAllByIdMemberId(memberId);
    }

}
