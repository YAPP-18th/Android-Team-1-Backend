package net.mureng.api.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.badge.service.BadgeAccomplishedService;
import net.mureng.core.core.exception.business.EntityNotFoundException;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.entity.MemberScrap;
import net.mureng.core.member.entity.MemberScrapPK;
import net.mureng.core.member.repository.MemberScrapRepository;
import net.mureng.core.todayexpression.entity.UsefulExpression;
import net.mureng.core.todayexpression.repository.UsefulExpressionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.mureng.core.core.message.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class MemberExpressionScrapService {

    private final UsefulExpressionRepository usefulExpressionRepository;
    private final MemberScrapRepository memberScrapRepository;
    private final BadgeAccomplishedService badgeAccomplishedService;

    @Transactional
    public MemberScrap scrapTodayExpression(Member member, Long expId){
        UsefulExpression usefulExpression = usefulExpressionRepository.findById(expId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXIST_EXPRESSION));

        MemberScrapPK memberScrapPK = new MemberScrapPK(member.getMemberId(), usefulExpression.getExpId());

        if(memberScrapRepository.existsById(memberScrapPK))
            throw new BadRequestException(ALREADY_SCRAPPED_EXPRESSION);

        MemberScrap memberScrap = MemberScrap.builder().id(memberScrapPK).member(member).usefulExpression(usefulExpression).build();

        memberScrapRepository.saveAndFlush(memberScrap);

        return memberScrap;
    }

    @Transactional
    public void deleteScrap(Member member, Long expId) {
        UsefulExpression usefulExpression = usefulExpressionRepository.findById(expId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXIST_EXPRESSION));

        MemberScrapPK memberScrapPK = new MemberScrapPK(member.getMemberId(), usefulExpression.getExpId());

        if(!memberScrapRepository.existsById(memberScrapPK))
            throw new BadRequestException(ALREADY_CANCELED_EXPRESSION);

        memberScrapRepository.deleteById(memberScrapPK);
    }

    @Transactional(readOnly = true)
    public List<MemberScrap> getMemberScrap(Long memberId){
        return memberScrapRepository.findAllByIdMemberId(memberId);
    }

}
