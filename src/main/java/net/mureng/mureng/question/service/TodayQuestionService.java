package net.mureng.mureng.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.repository.TodayQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodayQuestionService {
    private final TodayQuestionRepository todayQuestionRepository;

    @Transactional(readOnly = true)
    public Question getTodayQuestionByMemberId(Long memberId) {
        return todayQuestionRepository.findById(memberId).orElseThrow().getQuestion();
    }
}
