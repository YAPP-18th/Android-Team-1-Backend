package net.mureng.api.member.component;

import net.mureng.api.badge.component.BadgeMapper;
import net.mureng.api.member.dto.MemberAchievementDto;
import net.mureng.core.badge.entity.Badge;
import net.mureng.core.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MemberMapper.class, BadgeMapper.class})
public interface MemberAchievementMapper {

    @Mapping(target = "requesterProfile", expression = "java(member.isRequesterProfile(loggedInMember.getMemberId()))")
    MemberAchievementDto toDto(Member member, List<Badge> badges, Member loggedInMember);
}
