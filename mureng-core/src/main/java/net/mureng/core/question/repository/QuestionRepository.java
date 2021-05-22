package net.mureng.core.question.repository;

import net.mureng.core.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByAuthorMemberIdOrderByRegDateDesc(Long memberId);
    boolean existsByQuestionIdAndAuthorMemberId(Long questionId, Long memberId);

    @Query("Select q from Question q order by q.replies.size desc")
    Page<Question> findAllOrderByRepliesSizeDesc(Pageable page);
}
