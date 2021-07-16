package net.mureng.api.reply.component;

import net.mureng.api.member.component.MemberMapper;
import net.mureng.api.reply.dto.ReplyLikesDto;
import net.mureng.core.reply.entity.ReplyLikes;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ReplyMapper.class, MemberMapper.class })
public interface ReplyLikesMapper {

    @Mapping(source = "replyLikes.member.memberId", target = "memberId")
    @Mapping(source = "replyLikes.reply.replyId", target = "replyId")
    @Mapping(target = "likes", ignore = true)
    ReplyLikesDto toDto(ReplyLikes replyLikes);

    @BeanMapping(ignoreByDefault = true)
    ReplyLikes toEntity(ReplyLikesDto replyLikesDto);
}
