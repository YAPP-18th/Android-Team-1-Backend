package net.mureng.mureng.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByMemberIdMemberId(Long memberId);
}
