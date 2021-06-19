package net.mureng.core.question.repository;

import net.mureng.core.question.entity.TodayQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayQuestionRepository extends JpaRepository<TodayQuestion, Long> {
}
