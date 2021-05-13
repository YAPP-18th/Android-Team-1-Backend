package net.mureng.mureng.reply.repository;

import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.reply.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByMemberMemberId(Long memberId);
    List<Reply> findAllByQuestionQuestionId(Long questionId);
    Page<Reply> findAllByQuestionQuestionId(Long questionId, Pageable pageable);

    @Query("Select r from Reply r where r.question.questionId = :questionId order by r.replyLikes.size desc")
    Page<Reply> findAllByQuestionQuestionIdOrderByReplyLikesSize(Long questionId, Pageable pageable);

    boolean existsByRegDateBetweenAndMemberMemberId(LocalDateTime startDateTime, LocalDateTime endDateTime, Long memberId);
    Optional<Reply> findByMemberMemberIdAndQuestionQuestionId(Long memberId, Long questionId);

}
