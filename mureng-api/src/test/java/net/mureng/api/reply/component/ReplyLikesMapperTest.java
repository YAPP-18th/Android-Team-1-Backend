package net.mureng.api.reply.component;

import net.mureng.api.common.DtoCreator;
import net.mureng.api.reply.dto.ReplyLikesDto;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.reply.entity.ReplyLikes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ReplyLikesMapperTest {

    @Autowired
    private ReplyLikesMapper replyLikesMapper;

    private final ReplyLikes replyLikes = EntityCreator.createReplyLikesEntity();
    private final ReplyLikesDto replyLikesDto = DtoCreator.createReplyLikesDto();

    @Test
    public void 엔티티에서_DTO변환_테스트() {
        ReplyLikesDto mappedDto = replyLikesMapper.toDto(replyLikes);
        assertEquals(replyLikesDto.getReplyId(), mappedDto.getReplyId());
        assertEquals(replyLikesDto.getMemberId(), mappedDto.getMemberId());
        assertTrue(mappedDto.isLikes());
    }
}
