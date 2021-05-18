package net.mureng.mureng.question.component;

import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.dto.WordHintDto;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.entity.WordHint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface WordHintMapper extends EntityMapper<WordHint, WordHintDto> {
    @Override
    WordHintDto.ReadOnly toDto(WordHint wordHint);

    @Override
    @Mapping(target = "hintId", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    WordHint toEntity(WordHintDto wordHintDto);
}
