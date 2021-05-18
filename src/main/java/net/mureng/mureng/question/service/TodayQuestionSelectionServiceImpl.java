package net.mureng.mureng.question.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.component.NumberRandomizer;
import net.mureng.mureng.core.exception.MurengException;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.repository.QuestionRepository;
import net.mureng.mureng.question.repository.TodayQuestionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TodayQuestionSelectionServiceImpl implements TodayQuestionSelectionService {
    private static final int MAX_TOLERATE = 1000;

    private final QuestionRepository questionRepository;
    private final QuestionService questionService;
    private final TodayQuestionService todayQuestionService;
    private final NumberRandomizer numberRandomizer;

    @Override
    @Transactional
    public Question reselectTodayQuestion(Member member) {
        Set<Long> checked = new HashSet<>();
        Question question = todayQuestionService.getTodayQuestionByMemberId(member.getMemberId());
        checked.add(question.getQuestionId());

        int highestId = getHighestId();
        int retry = 0;
        while (checked.size() < highestId) {
            retry++;
            if (retry > highestId * MAX_TOLERATE) {
                throw new MurengException("질문 새로 고침이 실패하였습니다.");
            }

            long randomId = numberRandomizer.getRandomInt(highestId);
            if (checked.contains(randomId)) {
                continue;
            }

            if (! questionService.existsById(randomId) ||
                    questionService.isAlreadyReplied(randomId, member.getMemberId())) {
                checked.add(randomId);
                continue;
            }

            question = questionService.getQuestionById(randomId);
            break;
        }

        todayQuestionService.saveTodayQuestion(member.getMemberId(), question);
        return question;
    }

    private int getHighestId() {
        return (int)(long)questionRepository.findAll(PageRequest.of(0, 1,
                Sort.by(Sort.Direction.DESC, "regDate")))
                .getContent().get(0).getQuestionId();
    }
}
