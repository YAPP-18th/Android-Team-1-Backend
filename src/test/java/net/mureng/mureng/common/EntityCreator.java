package net.mureng.mureng.common;

import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.dto.WordHintDto;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.entity.WordHint;
import net.mureng.mureng.reply.dto.ReplyDto;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.entity.ReplyLikes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityCreator {
    private static final Long QUESTION_ID = 1L;
    private static final Long MEMBER_ID = 1L;
    private static final Long REPLY_ID = 1L;

    public static Question createQuestionEntity(){
        return Question.builder()
                .questionId(QUESTION_ID)
                .member(EntityCreator.createMemberEntity())
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

    public static Reply createReplyEntity(){
        return Reply.builder()
                .replyId(REPLY_ID)
                .author(createMemberEntity())
                .question(createQuestionEntity())
                .content("Test Reply")
                .image("image-path")
                .replyLikes(new HashSet<>(Arrays.asList(createReplyLikesEntity(), createReplyLikesEntity())))
                .build();
    }

    public static Member createMemberEntity() {
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

    public static ReplyLikes createReplyLikesEntity() {
        return ReplyLikes.builder()
                .member(createMemberEntity())
//                .reply(createReplyEntity())
                .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                .build();
    }

    public static MemberDto.ReadOnly createMemberDto() {
        return MemberDto.ReadOnly.builder()
                .memberId(1L)
                .identifier("123")
                .email("test@gmail.com")
                .nickname("Test")
                .murengCount(0)
                .attendanceCount(10)
                .lastAttendanceDate("2020-10-14")
                .isPushActive(true)
                .build();
    }

    public static QuestionDto.ReadOnly createQuesetionDto() {
        return QuestionDto.ReadOnly.builder()
                .questionId(1L)
                .category("카테고리")
                .content("This is english content.")
                .koContent("이것은 한글 내용입니다.")
                .wordHints(Set.of(createWordHintDto()))
                .repliesCount(2)
                .build();
    }

    public static ReplyDto.ReadOnly createReplyDto() {
        return ReplyDto.ReadOnly.builder()
                .questionId(QUESTION_ID)
                .replyId(REPLY_ID)
                .author(createMemberDto())
                .question(createQuesetionDto())
                .content("Test Reply")
                .image("image-path")
                .replyLikeCount(2)
                .build();
    }

    public static WordHint createWordHint() {
        return  WordHint.builder()
                .hintId(1L)
                .question(Question.builder().build())
                .word("apple")
                .meaning("사과")
                .regDate(LocalDateTime.parse("2020-10-14T11:00:00"))
                .build();
    }

    public static WordHintDto.ReadOnly createWordHintDto() {
        return WordHintDto.ReadOnly.builder()
                .hintId(1L)
                .word("apple")
                .meaning("사과")
                .build();
    }
}
