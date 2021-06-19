package net.mureng.core.reply.repository;

import net.mureng.core.reply.entity.ReplyLikes;
import net.mureng.core.reply.entity.ReplyLikesPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikesRepository extends JpaRepository<ReplyLikes, ReplyLikesPK> {
    int countByIdReplyId(Long replyId);
    Boolean existsByMemberMemberIdAndReplyReplyId(Long memberId, Long replyId);
}
