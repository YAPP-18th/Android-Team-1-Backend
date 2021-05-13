package net.mureng.mureng.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
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
    public boolean isAlreadyReplied(Long questionId, Long memberId) { return questionRepository.existsByQuestionIdAndMemberMemberId(questionId, memberId); }

    @Transactional(readOnly = true)
    public Page<Question> getQuestionList(Pageable pageable, String sort) {
        if(sort.equals("popular")) // TODO ENUM 으로 변경하면 더 직관적일 것 같다.
            return questionRepository.findAllOrderByRepliesSizeDesc(pageable);
        else if(sort.equals("newest"))
            return questionRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "regDate")));

        throw new BadRequestException("잘못된 요청입니다.");
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionWrittenByMember(Long memberId){
        return questionRepository.findAllByMemberMemberIdOrderByRegDateDesc(memberId);
    }
}
