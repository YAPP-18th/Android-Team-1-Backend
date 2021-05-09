package net.mureng.mureng.question.repository;

import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.entity.WordHint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordHintRepository extends JpaRepository<WordHint, Long> {
    List<WordHint> findAllByQuestion(Question question);
}
