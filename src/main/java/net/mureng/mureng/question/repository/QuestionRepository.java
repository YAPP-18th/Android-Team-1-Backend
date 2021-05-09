package net.mureng.mureng.question.repository;

import net.mureng.mureng.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByMemberMemberId(Long memberId);
}
