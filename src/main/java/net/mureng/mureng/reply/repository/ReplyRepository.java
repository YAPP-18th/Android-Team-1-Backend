package net.mureng.mureng.reply.repository;

import net.mureng.mureng.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByMemberMemberId(Long memberId);
    List<Reply> findAllByQuestionQuestionId(Long questionId);

}
