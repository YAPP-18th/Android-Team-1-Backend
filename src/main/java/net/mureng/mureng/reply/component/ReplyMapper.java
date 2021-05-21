package net.mureng.mureng.reply.component;

import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.member.component.MemberMapper;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.component.QuestionMapper;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.reply.dto.ReplyDto;
import net.mureng.mureng.reply.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { QuestionMapper.class, MemberMapper.class })
public interface ReplyMapper extends EntityMapper<Reply, ReplyDto> {
    @Override
    @Mapping(target = "replyLikeCount", expression = "java(reply.getReplyLikes().size())")
    @Mapping(target = "questionId", expression = "java(reply.getQuestion().getQuestionId())")
    @Mapping(target = "requestedByAuthor", ignore = true)
    ReplyDto.ReadOnly toDto(Reply reply);

    @Mapping(target = "image", source = "reply.image")
    @Mapping(target = "regDate", source = "reply.regDate")
    @Mapping(target = "replyLikeCount", expression = "java(reply.getReplyLikes().size())")
    @Mapping(target = "questionId", expression = "java(reply.getQuestion().getQuestionId())")
    @Mapping(target = "requestedByAuthor", expression = "java(reply.getAuthor().getMemberId()" +
            ".equals(loggedInMember.getMemberId()))")
    ReplyDto.ReadOnly toDto(Reply reply, Member loggedInMember);

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

    @Mapping(target = "replyId", ignore = true)
    @Mapping(target = "visible", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "modDate", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "replyLikes", ignore = true)
    @Mapping(target = "image", expression = "java(replyDto.getImage())")
    @Mapping(target = "content", expression = "java(replyDto.getContent())")
    @Mapping(source = "replyDto.questionId", target = "question.questionId")
    Reply toEntity(ReplyDto replyDto, Member author, Question question);

    @Mapping(target = "question", ignore = true)
    @Mapping(target = "visible", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "modDate", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "replyLikes", ignore = true)
    @Mapping(target = "image", expression = "java(replyDto.getImage())")
    Reply toEntity(ReplyDto replyDto, Member author, Long replyId);
}
