package net.mureng.api.question.component;

import net.mureng.api.core.component.EntityMapper;
import net.mureng.api.member.component.MemberMapper;
import net.mureng.core.member.entity.Member;
import net.mureng.api.question.dto.QuestionDto;
import net.mureng.core.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { WordHintMapper.class, MemberMapper.class })
public interface QuestionMapper extends EntityMapper<Question, QuestionDto> {
    @Override
    @Mapping(target = "repliesCount", expression = "java(question.getReplies().size())")
    QuestionDto.ReadOnly toDto(Question question);

    @Override
    @Mapping(target = "questionId", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "wordHints", ignore = true)
    Question toEntity(QuestionDto questionDto);

    @Mapping(target = "questionId", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "wordHints", ignore = true)
    Question toEntity(QuestionDto questionDto, Member author);
}
