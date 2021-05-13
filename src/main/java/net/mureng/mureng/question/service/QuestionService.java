package net.mureng.mureng.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Transactional(readOnly = true)
    public boolean existsById(Long questionId){
        return questionRepository.existsById(questionId);
    }

    @Transactional(readOnly = true)
    public boolean isAlreadyReplied(Long questionId, Long memberId) { return questionRepository.existsByQuestionIdAndMemberMemberId(questionId, memberId); }

    @Transactional(readOnly = true)
    public Page<Question> getQuestionList(int page, int size) {return questionRepository.findAll(PageRequest.of(page, size)); }
}
