package net.mureng.api.reply.component;

import net.mureng.api.member.component.MemberMapper;
import net.mureng.api.question.component.QuestionMapper;
import net.mureng.api.reply.dto.ReplyDto;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.entity.Question;
import net.mureng.core.reply.entity.Reply;
import org.mapstruct.*;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { QuestionMapper.class, MemberMapper.class })
public interface ReplyMapper {

    @Mapping(target = "image", source = "reply.image")
    @Mapping(target = "regDate", source = "reply.regDate")
    @Mapping(target = "replyLikeCount", expression = "java(reply.getReplyLikes().size())")
    @Mapping(target = "questionId", expression = "java(reply.getQuestion().getQuestionId())")
    @Mapping(target = "requestedByAuthor", expression = "java(reply.isAuthor(loggedInMember))")
    @Mapping(target = "likedByRequester", expression = "java(reply.likedByRequester(loggedInMember))")
    ReplyDto.ReadOnly toDto(Reply reply, Member loggedInMember);


    @Mapping(target = "modDate", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "question", source = "question")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "image", expression = "java(replyDto.getImage())")
    @Mapping(target = "content", expression = "java(replyDto.getContent())")
    Reply toEntityForPost(ReplyDto replyDto, Member author, Question question);


    @Mapping(target = "modDate", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "author", source = "author")
    @Mapping(target = "image", expression = "java(replyDto.getImage())")
    Reply toEntityForPut(ReplyDto replyDto, Member author, Long replyId);
}
