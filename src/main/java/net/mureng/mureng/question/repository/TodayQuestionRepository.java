package net.mureng.mureng.question.repository;

import net.mureng.mureng.question.entity.TodayQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodayQuestionRepository extends JpaRepository<TodayQuestion, Long> {
}
