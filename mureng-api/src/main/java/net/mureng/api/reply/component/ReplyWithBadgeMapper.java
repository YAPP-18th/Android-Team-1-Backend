package net.mureng.api.reply.component;

import net.mureng.api.member.component.MemberMapper;
import net.mureng.api.question.component.QuestionMapper;
import net.mureng.api.reply.dto.ReplyDto;
import net.mureng.core.member.entity.Member;
import net.mureng.core.reply.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { QuestionMapper.class, MemberMapper.class })
public interface ReplyWithBadgeMapper {
    @Mapping(target = "image", source = "reply.image")
    @Mapping(target = "regDate", source = "reply.regDate")
    @Mapping(target = "replyLikeCount", expression = "java(reply.getReplyLikes().size())")
    @Mapping(target = "questionId", expression = "java(reply.getQuestion().getQuestionId())")
    @Mapping(target = "requestedByAuthor", expression = "java(reply.isAuthor(loggedInMember))")
    @Mapping(target = "likedByRequester", expression = "java(reply.likedByRequester(loggedInMember))")
    @Mapping(target = "accomplishedBadge", expression = "java(isMureng3DaysAccomplished == true ? 1L : isMurengSetAccomplished == true ? 4L : 0L)")
    ReplyDto.ReadOnly toDto(Reply reply, Member loggedInMember, boolean isMureng3DaysAccomplished, boolean isMurengSetAccomplished);
}
