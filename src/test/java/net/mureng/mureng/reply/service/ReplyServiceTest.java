package net.mureng.mureng.reply.service;

import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.entity.WordHint;
import net.mureng.mureng.question.service.QuestionService;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {

    @InjectMocks
    private ReplyService replyService;

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private QuestionService questionService;

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

            Member member = createMemberEntity();
            Question question = createQuestionEntity();
            Reply newReply = createReplyEntity();

            // mocking
            given(replyRepository.existsByRegDateBetweenAndMemberMemberId(any(), any(), eq(notRepliedMemberId))).willReturn(false);
            given(questionService.existsById(eq(QUESTION_ID))).willReturn(true);
            given(questionService.isAlreadyReplied(eq(QUESTION_ID), eq(notRepliedMemberId))).willReturn(false);
            given(questionService.getQuestionById(eq(QUESTION_ID))).willReturn(question);
            given(replyRepository.saveAndFlush(any())).willReturn(newReply);

            // when
            Reply createdReply = replyService.create(member, QUESTION_ID, newReply);

            // then
            assertEquals(newReply.getReplyId(), createdReply.getReplyId());
            assertEquals(newReply.getContent(), createdReply.getContent());
            assertEquals(newReply.getImage(), createdReply.getImage());
        }

        @Test
        public void 답변_등록_예외_이미_오늘_답변한_사용자_테스트() {
            // given
            long repliedMemberId = 1;

            Member member = createMemberEntity();
            Reply newReply = createReplyEntity();

            // mocking
            given(replyRepository.existsByRegDateBetweenAndMemberMemberId(any(), any(), eq(repliedMemberId))).willReturn(true);

            // when
            BadRequestException exception =  assertThrows(BadRequestException.class, () -> replyService.create(member, QUESTION_ID, newReply));

            // then
            assertEquals("이미 오늘 답변한 사용자입니다.", exception.getMessage());
        }

        @Test
        public void 답변_등록_예외_존재하지않는_질문_테스트(){
            // given
            long notRepliedMemberId = 1;

            Member member = createMemberEntity();
            Reply newReply = createReplyEntity();

            // mocking
            given(replyRepository.existsByRegDateBetweenAndMemberMemberId(any(), any(), eq(notRepliedMemberId))).willReturn(false);
            given(questionService.existsById(eq(QUESTION_ID))).willReturn(false);

            // when
            BadRequestException exception =  assertThrows(BadRequestException.class, () -> replyService.create(member, QUESTION_ID, newReply));

            // then
            assertEquals("존재하지 않는 질문에 대한 요청입니다.", exception.getMessage());
        }

        @Test
        public void 답변_등록_예외_이미_답변한_질문_테스트() {
            // given
            long repliedMemberId = 1;

            Member member = createMemberEntity();
            Reply newReply = createReplyEntity();

            // mocking
            given(replyRepository.existsByRegDateBetweenAndMemberMemberId(any(), any(), eq(MEMBER_ID))).willReturn(false);
            given(questionService.existsById(eq(QUESTION_ID))).willReturn(true);
            given(questionService.isAlreadyReplied(eq(QUESTION_ID), eq(repliedMemberId))).willReturn(true);

            // when
            BadRequestException exception =  assertThrows(BadRequestException.class, () -> replyService.create(member, QUESTION_ID, newReply));

            // then
            assertEquals("이미 답변한 질문입니다.", exception.getMessage());
        }
    }

    @Nested
    class Update {
        @Test
        public void 답변_수정_테스트(){
            // given
            LocalDateTime now = LocalDateTime.now();

            Reply newReply = Reply.builder()
                            .content("New Test Content")
                            .image("New Image Path")
                            .modDate(now)
                            .build();

            Member member = createMemberEntity();
            Reply oldReply = createReplyEntity();
            oldReply.modifyReply(newReply);

            given(replyRepository.findById(eq(REPLY_ID))).willReturn(java.util.Optional.ofNullable(oldReply));
            given(replyRepository.saveAndFlush(any())).willReturn(oldReply);

            Reply modifiedReply = replyService.update(member, QUESTION_ID, newReply);

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
                    .content("New Test Content")
                    .image("New Image Path")
                    .modDate(now)
                    .build();

            Member member = createMemberEntity();
            Reply oldReply = createReplyEntity();
            oldReply.modifyReply(newReply);

            given(replyRepository.findById(eq(REPLY_ID))).willThrow(new BadRequestException("존재하지 않는 질문에 대한 요청입니다."));

            // when
            BadRequestException exception =  assertThrows(BadRequestException.class, () -> replyService.update(member, QUESTION_ID, newReply));

            // then
            assertEquals("존재하지 않는 질문에 대한 요청입니다.", exception.getMessage());
        }
    }

    private Question createQuestionEntity(){
        return Question.builder()
                .questionId(QUESTION_ID)
                .member(createMemberEntity())
                .category("카테고리")
                .content("This is english content.")
                .koContent("이것은 한글 내용입니다.")
                .regDate(LocalDateTime.parse("2020-10-14T11:00:00"))
                .wordHints(new HashSet<>(List.of(
                        WordHint.builder()
                                .hintId(1L)
                                .question(Question.builder().build())
                                .word("apple")
                                .meaning("사과")
                                .regDate(LocalDateTime.parse("2020-10-14T11:00:00"))
                                .build()
                )))
                .build();
    }

    private Reply createReplyEntity(){
        return Reply.builder()
                .replyId(REPLY_ID)
                .member(createMemberEntity())
                .question(createQuestionEntity())
                .content("Test Reply")
                .image("image-path")
                .build();
    }

    private Member createMemberEntity() {
        return Member.builder()
                .memberId(MEMBER_ID)
                .identifier("123")
                .email("test@email.com")
                .isActive(true)
                .nickname("Test")
                .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                .modDate(LocalDateTime.of(2020, 10, 14, 17, 11, 10))
                .murengCount(0)
                .build();
    }
}

