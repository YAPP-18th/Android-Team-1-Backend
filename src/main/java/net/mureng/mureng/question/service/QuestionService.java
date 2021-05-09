package net.mureng.mureng.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Question getQuestionById(Long questionId){
        return questionRepository.findById(questionId).orElseThrow();
    }
}
