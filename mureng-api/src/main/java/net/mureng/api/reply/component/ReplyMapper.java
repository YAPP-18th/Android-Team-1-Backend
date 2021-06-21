package net.mureng.api.reply.component;

import net.mureng.api.core.component.EntityMapper;
import net.mureng.api.member.component.MemberMapper;
import net.mureng.core.member.entity.Member;
import net.mureng.api.question.component.QuestionMapper;
import net.mureng.core.question.entity.Question;
import net.mureng.api.reply.dto.ReplyDto;
import net.mureng.core.reply.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { QuestionMapper.class, MemberMapper.class })
public interface ReplyMapper extends EntityMapper<Reply, ReplyDto> {
    @Override
    @Mapping(target = "replyLikeCount", expression = "java(reply.getReplyLikes().size())")
    @Mapping(target = "questionId", expression = "java(reply.getQuestion().getQuestionId())")
    @Mapping(target = "requestedByAuthor", ignore = true)
    @Mapping(target = "likedByRequester", ignore = true)
    ReplyDto.ReadOnly toDto(Reply reply);

    @Mapping(target = "image", source = "reply.image")
    @Mapping(target = "regDate", source = "reply.regDate")
    @Mapping(target = "replyLikeCount", expression = "java(reply.getReplyLikes().size())")
    @Mapping(target = "questionId", expression = "java(reply.getQuestion().getQuestionId())")
    @Mapping(target = "requestedByAuthor", expression = "java(reply.isAuthor(loggedInMember))")
    @Mapping(target = "likedByRequester", expression = "java(reply.likedByRequester(loggedInMember))")
    ReplyDto.ReadOnly toDto(Reply reply, Member loggedInMember);

    @Mapping(target = "image", source = "reply.image")
    @Mapping(target = "regDate", source = "reply.regDate")
    @Mapping(target = "replyLikeCount", expression = "java(reply.getReplyLikes().size())")
    @Mapping(target = "questionId", expression = "java(reply.getQuestion().getQuestionId())")
    @Mapping(target = "requestedByAuthor", expression = "java(reply.isAuthor(loggedInMember))")
    @Mapping(target = "likedByRequester", expression = "java(reply.likedByRequester(loggedInMember))")
    @Mapping(target = "accomplishedBadge", expression = "java(isMureng3DaysAccomplished == true ? 1L : isMurengSetAccomplished == true ? 4L : null)")
    ReplyDto.ReadOnly toDto(Reply reply, Member loggedInMember, boolean isMureng3DaysAccomplished, boolean isMurengSetAccomplished);

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
    @Mapping(target = "question", source = "question")
    @Mapping(target = "author", source = "author")
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
