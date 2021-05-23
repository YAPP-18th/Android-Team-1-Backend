package net.mureng.core.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.core.exception.AccessNotAllowedException;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.core.exception.ResourceNotFoundException;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.service.QuestionService;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final QuestionService questionService;

    @Value("${media.base.dir.name}")
    private String mediaBaseDirName;
    private String replyImageDirName = "/reply";

    @PostConstruct
    protected void init() {
        replyImageDirName = mediaBaseDirName + replyImageDirName;
    }

    @Transactional
    public Reply create(Reply newReply) {
        Long memberId = newReply.getAuthor().getMemberId();
        Long questionId = newReply.getQuestion().getQuestionId();

        if (isAlreadyReplied(memberId))
            throw new BadRequestException("이미 오늘 답변한 사용자입니다.");

        if (!questionService.existsById(questionId))
            throw new BadRequestException("존재하지 않는 질문에 대한 요청입니다.");

        if (questionService.isAlreadyReplied(questionId, memberId))
            throw new BadRequestException("이미 답변한 질문입니다.");

        newReply.setAuthor(newReply.getAuthor());
        newReply.setQuestion(questionService.getQuestionById(questionId));

        return replyRepository.saveAndFlush(newReply);
    }

    @Transactional(readOnly = true)
    public boolean isAlreadyReplied(Long memberId) {
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)); // 오늘 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)); //오늘 23:59:59

        return replyRepository.existsByRegDateBetweenAndAuthorMemberId(startDateTime, endDatetime, memberId);
    }

    @Transactional
    public Reply update(Reply newReply) {
        Long replyId = newReply.getReplyId();

        Reply oldReply = replyRepository.findById(replyId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 질문에 대한 요청입니다."));

        if (!oldReply.isAuthor(newReply.getAuthor()))
            throw new AccessNotAllowedException("접근 권한이 없습니다.");

        oldReply.modifyReply(newReply);

        return replyRepository.saveAndFlush(oldReply);
    }

    @Transactional
    public void delete(Member member, Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 답변에 대한 요청입니다."));

        if (!reply.isAuthor(member))
            throw new AccessNotAllowedException("접근 권한이 없습니다.");

        replyRepository.deleteById(replyId);
    }

    @Transactional(readOnly = true)
    public Reply findReplyByQuestionIdAndMember(Long memberId, Long questionId) {
        return replyRepository.findByAuthorMemberIdAndQuestionQuestionId(memberId, questionId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자 답변이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public List<Reply> findRepliesByMemberId(Long memberId){
        return replyRepository.findAllByAuthorMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Reply findById(Long replyId){
        return replyRepository.findById(replyId).orElseThrow(() -> new BadRequestException("존재하지 않는 답변에 대한 요청입니다."));
    }
}
