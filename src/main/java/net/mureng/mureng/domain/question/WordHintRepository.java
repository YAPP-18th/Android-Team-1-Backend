package net.mureng.mureng.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordHintRepository extends JpaRepository<WordHint, Long> {
    List<WordHint> findAllByQuestionId(Question questionId);

}
