package net.mureng.api.todayexpression.component;

import net.mureng.api.todayexpression.dto.TodayExpressionDto;
import net.mureng.core.member.entity.Member;
import net.mureng.core.todayexpression.entity.TodayExpression;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodayExpressionMapper {

    @Mapping(target = "scrappedByRequester", expression = "java(todayExpression.scrappedByRequester(loggedInMember))")
    TodayExpressionDto toDto(TodayExpression todayExpression, @Context Member loggedInMember);

}
