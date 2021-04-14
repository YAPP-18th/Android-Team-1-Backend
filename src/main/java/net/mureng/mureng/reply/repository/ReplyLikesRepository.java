package net.mureng.mureng.reply.repository;

import net.mureng.mureng.reply.entity.ReplyLikes;
import net.mureng.mureng.reply.entity.ReplyLikesPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikesRepository extends JpaRepository<ReplyLikes, ReplyLikesPK> {
    int countByIdReplyId(Long replyId);
}
