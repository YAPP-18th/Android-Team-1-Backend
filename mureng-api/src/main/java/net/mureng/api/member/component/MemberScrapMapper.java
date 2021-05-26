package net.mureng.api.member.component;

import net.mureng.api.core.component.EntityMapper;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;
import net.mureng.core.member.entity.MemberScrap;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberScrapMapper extends EntityMapper<MemberScrap, TodayExpressionDto> {
    @Override
    @Mapping(target = "expId", source = "memberScrap.todayExpression.expId")
    @Mapping(target = "expression", source = "memberScrap.todayExpression.expression")
    @Mapping(target = "meaning", source = "memberScrap.todayExpression.meaning")
    @Mapping(target = "expressionExample", source = "memberScrap.todayExpression.expressionExample")
    @Mapping(target = "expressionExampleMeaning", source = "memberScrap.todayExpression.expressionExampleMeaning")
    @Mapping(target = "scrappedByRequester", constant = "true")
    TodayExpressionDto toDto(MemberScrap memberScrap);

    @Override
    @BeanMapping(ignoreByDefault = true)
    MemberScrap toEntity(TodayExpressionDto todayExpressionDto);
}
