package net.mureng.mureng.domain.question;

import net.mureng.mureng.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByMemberId(Member memberId);
}
