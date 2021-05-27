package net.mureng.api.member.component;

import net.mureng.api.member.dto.MemberProfileDto;
import net.mureng.api.todayexpression.component.TodayExpressionMapper;
import net.mureng.core.member.entity.Member;
import net.mureng.core.todayexpression.entity.TodayExpression;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",  uses = { MemberMapper.class, TodayExpressionMapper.class})
public interface MemberProfileMapper {

    @Mapping(source = "member", target = "member")
    @Mapping(source = "scrapList", target = "scrapList")
    @Mapping(target = "requesterProfile", expression = "java(member.isRequesterProfile(loggedInMember.getMemberId()))")
    MemberProfileDto toDto(Member member, List<TodayExpression> scrapList, @Context Member loggedInMember);
}
