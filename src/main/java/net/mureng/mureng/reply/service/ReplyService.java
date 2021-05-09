package net.mureng.mureng.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.service.QuestionService;
import net.mureng.mureng.reply.component.ReplyMapper;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;
    private final QuestionService questionService;

    @Transactional
    public Reply postReply(Member member, Long questionId, Reply newReply) {
        Long memberId = member.getMemberId();

        if(isAlreadyReplied(memberId))
            throw new BadRequestException("이미 오늘 답변한 사용자입니다.");

        if(!questionService.existsById(questionId))
            throw new BadRequestException("존재하지 않는 질문에 대한 요청입니다.");

        if(questionService.isAlreadyReplied(questionId, memberId))
            throw new BadRequestException("이미 답변한 질문입니다.");

        newReply.setMember(member);
        newReply.setQuestion(questionService.getQuestionById(questionId));

        return replyRepository.saveAndFlush(newReply);
    }

    @Transactional(readOnly = true)
    public boolean isAlreadyReplied(Long memberId){
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)); // 오늘 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 23:59:59

        return replyRepository.existsByRegDateBetweenAndMemberMemberId(startDateTime, endDatetime, memberId);
    }
}
