package net.mureng.core.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.core.exception.AccessNotAllowedException;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.core.exception.ResourceNotFoundException;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.MemberRepository;
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

import static net.mureng.core.core.message.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final QuestionService questionService;
    private final ReplyPostProcessService replyPostProcessService;
    private final MemberRepository memberRepository;

    @Value("${media.base.dir.name}")
    private String mediaBaseDirName;
    private String replyImageDirName = "/reply";

    @Value("${spring.profiles.active}")
    private String profile;

    @PostConstruct
    protected void init() {
        replyImageDirName = mediaBaseDirName + replyImageDirName;
    }

    @Transactional
    public Reply create(Reply newReply) {
        Long memberId = newReply.getAuthor().getMemberId();
        Long questionId = newReply.getQuestion().getQuestionId();

        if (! isTest() && isAlreadyRepliedToday(memberId))
            throw new BadRequestException(ALREADY_ANSWERED_MEMBER);

        if (!questionService.existsById(questionId))
            throw new ResourceNotFoundException(NOT_EXIST_QUESTION);

        if (isQuestionAlreadyReplied(questionId, memberId))
            throw new BadRequestException(ALREADY_ANSWERED_REPLY);

        newReply.setAuthor(newReply.getAuthor());
        newReply.setQuestion(questionService.getQuestionById(questionId));

        Reply savedReply = replyRepository.saveAndFlush(newReply);
        replyPostProcessService.postProcess(savedReply);

        return savedReply;
    }

    private boolean isTest() {
        return "dev".equals(profile);
    }

    @Transactional(readOnly = true)
    public boolean isQuestionAlreadyReplied(Long questionId, Long authorId) {
        return replyRepository.existsByQuestionQuestionIdAndAuthorMemberId(questionId, authorId);
    }

    @Transactional(readOnly = true)
    public boolean isAlreadyRepliedToday(Long memberId) {
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)); // 오늘 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)); //오늘 23:59:59

        return replyRepository.existsByRegDateBetweenAndAuthorMemberId(startDateTime, endDatetime, memberId);
    }

    @Transactional
    public Reply update(Reply newReply) {
        Long replyId = newReply.getReplyId();

        Reply oldReply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_REPLY));

        if (!oldReply.isAuthor(newReply.getAuthor()))
            throw new AccessNotAllowedException(UNAUTHORIZED);

        oldReply.modifyReply(newReply);

        return replyRepository.saveAndFlush(oldReply);
    }

    @Transactional
    public void delete(Member member, Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_REPLY));

        if (!reply.isAuthor(member))
            throw new AccessNotAllowedException(UNAUTHORIZED);

        replyRepository.deleteById(replyId);
    }

    @Transactional(readOnly = true)
    public Reply findReplyByQuestionIdAndMember(Long memberId, Long questionId) {
        return replyRepository.findByAuthorMemberIdAndQuestionQuestionId(memberId, questionId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_REPLY));
    }

    @Transactional(readOnly = true)
    public List<Reply> findRepliesByMemberId(Long memberId){
        return replyRepository.findAllByAuthorMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Reply findById(Long replyId){
        return replyRepository.findById(replyId).orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_REPLY));
    }
}
