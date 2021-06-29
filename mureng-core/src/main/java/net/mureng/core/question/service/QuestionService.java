package net.mureng.core.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.core.exception.ResourceNotFoundException;
import net.mureng.core.question.entity.Question;
import net.mureng.core.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.mureng.core.core.message.ErrorMessage.NOT_EXIST_QUESTION;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Question getQuestionById(Long questionId){
        return questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_QUESTION));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long questionId){
        return questionRepository.existsById(questionId);
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionWrittenByMember(Long memberId){
        return questionRepository.findAllByAuthorMemberIdOrderByRegDateDesc(memberId);
    }

    @Transactional
    public Question create(Question question){
        return questionRepository.saveAndFlush(question);
    }
}
