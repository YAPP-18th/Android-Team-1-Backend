package net.mureng.mureng.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.exception.AccessNotAllowedException;
import net.mureng.mureng.core.component.FileUploader;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.service.QuestionService;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final QuestionService questionService;
    private final FileUploader fileUploader;

    @Value("${media.base.dir.name}")
    private String mediaBaseDirName;
    private String replyImageDirName = "/reply";

    @PostConstruct
    protected void init() {
        replyImageDirName = mediaBaseDirName + replyImageDirName;
    }

    // TODO 이걸 3개의 인자 대신 member와 question 모두 newReply 하나의 인자로도 해결 가능할 듯!
    // 메서드 인자는 가능하면 하나만 받는게 좋음!
    @Transactional
    public Reply create(Member member, Long questionId, Reply newReply) {
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

        return replyRepository.existsByRegDateBetweenAndMemberMemberId (startDateTime, endDatetime, memberId);
    }

    @Transactional
    public Reply update(Member member, Long replyId, Reply newReply) {
        Reply oldReply =  replyRepository.findById(replyId)
                                            .orElseThrow(() -> new BadRequestException("존재하지 않는 질문에 대한 요청입니다."));

        if(!oldReply.isWriter(member))
            throw new AccessNotAllowedException("접근 권한이 없습니다.");

        oldReply.modifyReply(newReply);

        return replyRepository.saveAndFlush(oldReply);
    }

    @Transactional
    public void delete(Member member, Long replyId){
        Reply reply = replyRepository.findById(replyId)
                                        .orElseThrow(() -> new BadRequestException("존재하지 않는 답변에 대한 요청입니다."));

        if(!reply.isWriter(member))
            throw new AccessNotAllowedException("접근 권한이 없습니다.");

        replyRepository.deleteById(replyId);
    }

    /**
     * Multipart File 을 저장하고, 저장 경로를 리턴한다.
     *
     * @param imageFile 요청으로 들어온 Multipart File
     * @return 웹상에서 저장된 경로
     */
    public String uploadReplyImageFile(MultipartFile imageFile) {
        return fileUploader.saveMultiPartFile(imageFile, replyImageDirName)
                .replace(mediaBaseDirName, "");
    }
}
