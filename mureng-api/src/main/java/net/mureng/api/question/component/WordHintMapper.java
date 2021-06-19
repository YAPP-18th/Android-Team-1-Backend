package net.mureng.api.question.component;

import net.mureng.api.core.component.EntityMapper;
import net.mureng.api.question.dto.WordHintDto;
import net.mureng.core.question.entity.WordHint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
