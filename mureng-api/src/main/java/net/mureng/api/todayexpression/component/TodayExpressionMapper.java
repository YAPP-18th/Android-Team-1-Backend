package net.mureng.api.todayexpression.component;

import net.mureng.api.todayexpression.dto.UsefulExpressionDto;
import net.mureng.core.member.entity.Member;
import net.mureng.core.todayexpression.entity.UsefulExpression;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodayExpressionMapper {

    @Mapping(target = "scrappedByRequester", expression = "java(usefulExpression.scrappedByRequester(loggedInMember))")
    @Mapping(target = "accomplishedBadge", ignore = true)
    UsefulExpressionDto toDto(UsefulExpression usefulExpression, @Context Member loggedInMember);

    @Mapping(target = "scrappedByRequester", expression = "java(usefulExpression.scrappedByRequester(loggedInMember))")
    @Mapping(target = "accomplishedBadge", expression = "java(isAcademicMurengAccomplished == true ? 3L : 0L)")
    UsefulExpressionDto toDtoWithBadge(UsefulExpression usefulExpression, @Context Member loggedInMember, Boolean isAcademicMurengAccomplished);

}
