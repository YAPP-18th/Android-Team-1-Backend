package net.mureng.core.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.question.entity.Question;
import net.mureng.core.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Question getQuestionById(Long questionId){
        return questionRepository.findById(questionId).orElseThrow(() -> new BadRequestException("존재하지 않는 질문입니다."));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long questionId){
        return questionRepository.existsById(questionId);
    }

    @Transactional(readOnly = true)
    public boolean isAlreadyReplied(Long questionId, Long memberId) { return questionRepository.existsByQuestionIdAndAuthorMemberId(questionId, memberId); }

    @Transactional(readOnly = true)
    public List<Question> getQuestionWrittenByMember(Long memberId){
        return questionRepository.findAllByAuthorMemberIdOrderByRegDateDesc(memberId);
    }

    @Transactional
    public Question create(Question question){
        return questionRepository.saveAndFlush(question);
    }
}
