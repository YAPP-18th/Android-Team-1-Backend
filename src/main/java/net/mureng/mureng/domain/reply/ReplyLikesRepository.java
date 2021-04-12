package net.mureng.mureng.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikesRepository extends JpaRepository<ReplyLikes, ReplyLikesPK> {
    Long countAllByIdReplyId(Long replyId);
    Long countByIdReplyId(Long replyId);
}
