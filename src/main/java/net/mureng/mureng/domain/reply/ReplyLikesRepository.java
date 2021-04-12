package net.mureng.mureng.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikesRepository extends JpaRepository<ReplyLikes, ReplyLikesPK> {
    Long countByIdReplyId(Long replyId);
}
