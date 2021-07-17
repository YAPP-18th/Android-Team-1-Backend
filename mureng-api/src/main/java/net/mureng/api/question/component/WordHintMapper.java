package net.mureng.api.question.component;

import net.mureng.api.question.dto.WordHintDto;
import net.mureng.core.question.entity.WordHint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WordHintMapper {
    WordHintDto.ReadOnly toDto(WordHint wordHint);

    WordHint toEntity(WordHintDto wordHintDto);
}
