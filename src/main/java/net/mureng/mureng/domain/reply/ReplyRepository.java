package net.mureng.mureng.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByMemberIdMemberId(Long memberId);
    List<Reply> findAllByQuestionIdQuestionId(Long questionId);

}
