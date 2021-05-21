package net.mureng.mureng.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.dto.ApiPageRequest;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
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
    public Page<Question> getQuestionList(ApiPageRequest pageRequest) {
        if(pageRequest.getSort() == ApiPageRequest.PageSort.POPULAR)
            return questionRepository.findAllOrderByRepliesSizeDesc(pageRequest.convert());

        return questionRepository.findAll(pageRequest.convertWithNewestSort());
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
