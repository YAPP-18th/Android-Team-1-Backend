package net.mureng.mureng.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<Question> getQuestionList(int page, int size, String sort) {
        if(sort.equals("popular"))
            return questionRepository.findAllOrderByRepliesSizeDesc(PageRequest.of(page, size));
        else if(sort.equals("newest"))
            return questionRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate")));
        else
            throw new BadRequestException("잘못된 요청입니다.");
    }
}
