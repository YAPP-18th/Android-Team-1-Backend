package net.mureng.mureng.reply.component;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.reply.dto.ReplyDto;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.entity.ReplyLikes;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReplyMapper extends EntityMapper {
    @Override
    protected void initEntityToDtoMapping() {
        final Converter<Set<ReplyLikes>, Integer> repliesCountConverter = context -> {
            if (context == null)
                return null;

            return context.getSource().size();
        };

        modelMapper.createTypeMap(Reply.class, ReplyDto.class)
                .addMappings(mapper -> mapper.skip(ReplyDto::setQuestionId))
                .addMappings(mapper -> mapper.using(repliesCountConverter)
                        .map(Reply::getReplyLikes, ReplyDto::setReplyLikeCount));
    }

    @Override
    protected void initDtoToEntityMapping() {
        modelMapper.createTypeMap(ReplyDto.class, Reply.class)
                .addMappings(mapper -> mapper.skip(Reply::setMember))
                .addMappings(mapper -> mapper.skip(Reply::setQuestion))
                .addMappings(mapper -> mapper.skip(Reply::setVisible))
                .addMappings(mapper -> mapper.skip(Reply::setDeleted))
                .addMappings(mapper -> mapper.skip(Reply::setModDate))
                .addMappings(mapper -> mapper.skip(Reply::setRegDate))
                .addMappings(mapper -> mapper.skip(Reply::setReplyLikes));
    }

    public Reply map(ReplyDto replyDto) { return modelMapper.map(replyDto, Reply.class); }

    public ReplyDto map(Reply reply) { return modelMapper.map(reply, ReplyDto.class); }
}
