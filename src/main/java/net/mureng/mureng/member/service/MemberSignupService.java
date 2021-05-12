package net.mureng.mureng.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.entity.TodayQuestion;
import net.mureng.mureng.question.repository.TodayQuestionRepository;
import net.mureng.mureng.question.service.TodayQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Service
@RequiredArgsConstructor
public class MemberSignupService {
    private final MemberService memberService;
    private final TodayQuestionRepository todayQuestionRepository;

    @Transactional
    public Member signup(@Valid Member newMember) {
        Member savedMember = memberService.saveMember(newMember);
        afterSignUp(savedMember);
        return savedMember;
    }

    private void afterSignUp(Member newMember) {
        saveTodayQuestionForNewMember(newMember);
    }

    private void saveTodayQuestionForNewMember(Member newMember) {
        todayQuestionRepository.save(TodayQuestion.builder()
                .member(newMember)
                .question(Question.builder().questionId(1L).build()) // question_id 가 1인 질문이 있다는 가정
                .build());
    }
}
