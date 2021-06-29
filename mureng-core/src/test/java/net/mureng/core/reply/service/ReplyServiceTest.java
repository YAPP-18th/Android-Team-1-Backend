package net.mureng.core.reply.service;

import net.mureng.core.common.EntityCreator;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.core.exception.ResourceNotFoundException;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.service.QuestionService;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static net.mureng.core.core.message.ErrorMessage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {

    @InjectMocks
    private ReplyService replyService;

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private QuestionService questionService;

    @Mock
    private ReplyPostProcessService replyPostProcessService;

    private static final Long MEMBER_ID = 1L;
    private static final Long QUESTION_ID = 1L;
    private static final Long REPLY_ID = 1L;

    @Nested
    @DisplayName("답변 등록 테스트")
    class Create {
        @Test
        public void 답변_등록_테스트() {
            // given
            long notRepliedMemberId = 1;

            Reply newReply = EntityCreator.createReplyEntity();

            // mocking
            given(replyRepository.existsByRegDateBetweenAndAuthorMemberId(any(), any(), eq(notRepliedMemberId))).willReturn(false);
            given(questionService.existsById(eq(QUESTION_ID))).willReturn(true);
            given(replyService.isQuestionAlreadyReplied(eq(QUESTION_ID), eq(notRepliedMemberId))).willReturn(false);
            given(questionService.getQuestionById(eq(QUESTION_ID))).willReturn(newReply.getQuestion());
            given(replyRepository.saveAndFlush(any())).willReturn(newReply);
            doNothing().when(replyPostProcessService).postProcess(any());

            // when
            Reply createdReply = replyService.create(newReply);

            // then
            assertEquals(newReply.getReplyId(), createdReply.getReplyId());
            assertEquals(newReply.getContent(), createdReply.getContent());
            assertEquals(newReply.getImage(), createdReply.getImage());
        }

        @Test
        public void 답변_등록_예외_이미_오늘_답변한_사용자_테스트() {
            // given
            long repliedMemberId = 1;

            Reply newReply = EntityCreator.createReplyEntity();

            // mocking
            given(replyRepository.existsByRegDateBetweenAndAuthorMemberId(any(), any(), eq(repliedMemberId))).willReturn(true);

            // when
            BadRequestException exception =  assertThrows(BadRequestException.class, () -> replyService.create(newReply));

            // then
            assertEquals(ALREADY_ANSWERED_MEMBER, exception.getMessage());
        }

        @Test
        public void 답변_등록_예외_존재하지않는_질문_테스트(){
            // given
            long notRepliedMemberId = 1;

            Reply newReply = EntityCreator.createReplyEntity();

            // mocking
            given(replyRepository.existsByRegDateBetweenAndAuthorMemberId(any(), any(), eq(notRepliedMemberId))).willReturn(false);
            given(questionService.existsById(eq(QUESTION_ID))).willReturn(false);

            // when
            ResourceNotFoundException exception =  assertThrows(ResourceNotFoundException.class, () -> replyService.create(newReply));

            // then
            assertEquals(NOT_EXIST_QUESTION, exception.getMessage());
        }

        @Test
        public void 답변_등록_예외_이미_답변한_질문_테스트() {
            // given
            long repliedMemberId = 1;

            Reply newReply = EntityCreator.createReplyEntity();

            // mocking
            given(replyRepository.existsByRegDateBetweenAndAuthorMemberId(any(), any(), eq(MEMBER_ID))).willReturn(false);
            given(questionService.existsById(eq(QUESTION_ID))).willReturn(true);
            given(replyService.isQuestionAlreadyReplied(eq(QUESTION_ID), eq(repliedMemberId))).willReturn(true);

            // when
            BadRequestException exception =  assertThrows(BadRequestException.class, () -> replyService.create(newReply));

            // then
            assertEquals(ALREADY_ANSWERED_REPLY, exception.getMessage());
        }
    }

    @Nested
    class Update {
        @Test
        public void 답변_수정_테스트(){
            // given
            LocalDateTime now = LocalDateTime.now();

            Reply newReply = Reply.builder()
                            .replyId(REPLY_ID)
                            .author(EntityCreator.createMemberEntity())
                            .content("New Test Content")
                            .image("New Image Path")
                            .modDate(now)
                            .build();

            Reply oldReply = EntityCreator.createReplyEntity();
            oldReply.modifyReply(newReply);

            given(replyRepository.findById(eq(REPLY_ID))).willReturn(java.util.Optional.ofNullable(oldReply));
            given(replyRepository.saveAndFlush(any())).willReturn(oldReply);

            Reply modifiedReply = replyService.update(newReply);

            // then
            assertEquals(oldReply.getReplyId(), modifiedReply.getReplyId());
            assertEquals(oldReply.getContent(), modifiedReply.getContent());
            assertEquals(oldReply.getImage(), modifiedReply.getImage());
            assertEquals(oldReply.getModDate(), modifiedReply.getModDate());
        }

        @Test
        public void 답변_수정_예외_존재하지않는_질문_테스트(){
            // given
            LocalDateTime now = LocalDateTime.now();

            Reply newReply = Reply.builder()
                            .replyId(REPLY_ID)
                            .author(EntityCreator.createMemberEntity())
                            .content("New Test Content")
                            .image("New Image Path")
                            .modDate(now)
                            .build();

            Member member = EntityCreator.createMemberEntity();
            Reply oldReply = EntityCreator.createReplyEntity();
            oldReply.modifyReply(newReply);

            given(replyRepository.findById(eq(REPLY_ID))).willThrow(new ResourceNotFoundException(NOT_EXIST_REPLY));

            // when
            ResourceNotFoundException exception =  assertThrows(ResourceNotFoundException.class, () -> replyService.update(newReply));

            // then
            assertEquals(NOT_EXIST_REPLY, exception.getMessage());
        }
    }


}

