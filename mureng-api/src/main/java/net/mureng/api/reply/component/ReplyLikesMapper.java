package net.mureng.api.reply.component;

import net.mureng.api.member.component.MemberMapper;
import net.mureng.api.reply.dto.ReplyLikesDto;
import net.mureng.core.reply.entity.ReplyLikes;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { ReplyMapper.class, MemberMapper.class })
public interface ReplyLikesMapper {

    @Mapping(source = "replyLikes.member.memberId", target = "memberId")
    @Mapping(source = "replyLikes.reply.replyId", target = "replyId")
    ReplyLikesDto toDto(ReplyLikes replyLikes);
}
