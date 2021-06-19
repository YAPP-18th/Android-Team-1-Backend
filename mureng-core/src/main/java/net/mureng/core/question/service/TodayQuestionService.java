package net.mureng.core.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.question.entity.Question;
import net.mureng.core.question.entity.TodayQuestion;
import net.mureng.core.question.repository.TodayQuestionRepository;
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

    @Transactional
    public void saveTodayQuestion(Long memberId, Question question) {
        TodayQuestion todayQuestion = todayQuestionRepository.findById(memberId).orElseThrow();
        todayQuestion.setQuestion(question);
        todayQuestionRepository.saveAndFlush(todayQuestion);
    }
}
