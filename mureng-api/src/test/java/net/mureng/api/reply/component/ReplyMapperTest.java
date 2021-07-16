package net.mureng.api.reply.component;

import net.mureng.api.common.DtoCreator;
import net.mureng.core.badge.service.BadgeAccomplishedServiceImpl;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.question.entity.Question;
import net.mureng.api.reply.dto.ReplyDto;
import net.mureng.core.reply.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ReplyMapperTest {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private ReplyWithBadgeMapper replyWithBadgeMapper;

    private final Reply reply = EntityCreator.createReplyEntity();
    private final ReplyDto.ReadOnly replyDto = DtoCreator.createReplyDto();

    @Test
    public void 엔티티에서_DTO변환_로그인사용자_테스트() {
        ReplyDto.ReadOnly mappedDto = replyMapper.toDto(reply, EntityCreator.createMemberEntity());
        assertEquals(replyDto.getReplyId(), mappedDto.getReplyId());
        assertEquals(replyDto.getContent(), mappedDto.getContent());
        assertEquals(replyDto.getAuthor().getMemberId(), mappedDto.getAuthor().getMemberId());
        assertEquals(replyDto.getQuestion().getQuestionId(), mappedDto.getQuestion().getQuestionId());
        assertEquals(replyDto.getReplyLikeCount(), mappedDto.getReplyLikeCount());
        assertEquals(replyDto.getRequestedByAuthor(), mappedDto.getRequestedByAuthor());
        assertEquals(replyDto.getLikedByRequester(), mappedDto.getLikedByRequester());
    }

    @Test
    public void 엔티티에서_DTO변환_로그인사용자_테스트_뱃지획득() {
        ReplyDto.ReadOnly mappedDto = replyWithBadgeMapper.toDto(reply, EntityCreator.createMemberEntity(), false, true);
        assertEquals(replyDto.getReplyId(), mappedDto.getReplyId());
        assertEquals(replyDto.getContent(), mappedDto.getContent());
        assertEquals(replyDto.getAuthor().getMemberId(), mappedDto.getAuthor().getMemberId());
        assertEquals(replyDto.getQuestion().getQuestionId(), mappedDto.getQuestion().getQuestionId());
        assertEquals(replyDto.getReplyLikeCount(), mappedDto.getReplyLikeCount());
        assertEquals(replyDto.getRequestedByAuthor(), mappedDto.getRequestedByAuthor());
        assertEquals(replyDto.getLikedByRequester(), mappedDto.getLikedByRequester());
        assertEquals(BadgeAccomplishedServiceImpl.MurengSet.id, mappedDto.getAccomplishedBadge());
    }

    @Test
    public void DTO에서_엔티티변환_답변생성할때_테스트() {
        Reply mappedEntity = replyMapper.toEntityForPost(replyDto, EntityCreator.createMemberEntity(), Question.builder().questionId(replyDto.getQuestionId()).build());
        assertEquals(replyDto.getQuestionId(), mappedEntity.getQuestion().getQuestionId());
        assertEquals(reply.getContent(), mappedEntity.getContent());
        assertEquals(reply.getAuthor().getEmail(), mappedEntity.getAuthor().getEmail());
    }

    @Test
    public void DTO에서_엔티티변환_답변수정할때_테스트() {
        Reply mappedEntity = replyMapper.toEntityForPut(replyDto, EntityCreator.createMemberEntity(), 2L);
        assertEquals(2L, mappedEntity.getReplyId());
        assertEquals(reply.getContent(), mappedEntity.getContent());
        assertEquals(reply.getAuthor().getEmail(), mappedEntity.getAuthor().getEmail());
    }

}