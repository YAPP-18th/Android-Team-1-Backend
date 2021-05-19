package net.mureng.mureng.question.component;

import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { WordHintMapper.class })
public interface QuestionMapper extends EntityMapper<Question, QuestionDto> {
    @Override
    @Mapping(target = "repliesCount", expression = "java(question.getReplies().size())")
    QuestionDto.ReadOnly toDto(Question question);

    @Override
    @Mapping(target = "questionId", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "wordHints", ignore = true)
    Question toEntity(QuestionDto questionDto);

    @Mapping(target = "questionId", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "wordHints", ignore = true)
    Question toEntity(QuestionDto questionDto, Member member);
}
