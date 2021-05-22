package net.mureng.core.question.repository;

import net.mureng.core.question.entity.Question;
import net.mureng.core.question.entity.WordHint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordHintRepository extends JpaRepository<WordHint, Long> {
    List<WordHint> findAllByQuestion(Question question);
}
