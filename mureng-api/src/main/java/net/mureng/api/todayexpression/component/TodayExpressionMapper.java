package net.mureng.api.todayexpression.component;

import net.mureng.api.core.component.EntityMapper;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;
import net.mureng.core.member.entity.Member;
import net.mureng.core.todayexpression.entity.TodayExpression;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodayExpressionMapper extends EntityMapper<TodayExpression, TodayExpressionDto> {

    @Override
    @BeanMapping(ignoreByDefault = true)
    TodayExpressionDto toDto(TodayExpression todayExpression);

    @Mapping(target = "scrappedByRequester", expression = "java(todayExpression.scrappedByRequester(loggedInMember))")
    TodayExpressionDto toDto(TodayExpression todayExpression,  Member loggedInMember);

    @Override
    @BeanMapping(ignoreByDefault = true)
    TodayExpression toEntity(TodayExpressionDto todayExpressionDto);
}
