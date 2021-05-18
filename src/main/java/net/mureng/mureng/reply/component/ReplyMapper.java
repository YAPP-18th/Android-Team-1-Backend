package net.mureng.mureng.reply.component;

import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.member.component.MemberMapper;
import net.mureng.mureng.question.component.QuestionMapper;
import net.mureng.mureng.question.component.WordHintMapper;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.reply.dto.ReplyDto;
import net.mureng.mureng.reply.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { QuestionMapper.class, MemberMapper.class })
public interface ReplyMapper extends EntityMapper<Reply, ReplyDto> {
    @Override
    @Mapping(target = "replyLikeCount", expression = "java(reply.getReplyLikes().size())")
    @Mapping(target = "questionId", expression = "java(reply.getQuestion().getQuestionId())")
    ReplyDto.ReadOnly toDto(Reply reply);

    @Override
    @Mapping(target = "replyId", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "visible", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "modDate", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "replyLikes", ignore = true)
    Reply toEntity(ReplyDto replyDto);
}
