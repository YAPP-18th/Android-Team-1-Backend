package net.mureng.mureng.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodayQuestionRepository extends JpaRepository<TodayQuestion, Long> {
}
