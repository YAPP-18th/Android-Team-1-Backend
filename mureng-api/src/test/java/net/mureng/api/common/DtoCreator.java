package net.mureng.api.common;

import net.mureng.api.member.dto.MemberDto;
import net.mureng.api.question.dto.QuestionDto;
import net.mureng.api.question.dto.WordHintDto;
import net.mureng.api.reply.dto.ReplyDto;
import net.mureng.api.reply.dto.ReplyLikesDto;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;

import java.util.Set;

public class DtoCreator {
    private static final Long QUESTION_ID = 1L;
    private static final Long MEMBER_ID = 1L;
    private static final Long REPLY_ID = 1L;

    public static MemberDto.ReadOnly createMemberDto() {
        return MemberDto.ReadOnly.builder()
                .memberId(MEMBER_ID)
                .identifier("123")
                .email("test@gmail.com")
                .nickname("Test")
                .murengCount(0)
                .attendanceCount(10)
                .lastAttendanceDate("2020-10-14")
                .isPushActive(true)
                .build();
    }

    public static QuestionDto.ReadOnly createQuestionDto() {
        return QuestionDto.ReadOnly.builder()
                .questionId(QUESTION_ID)
                .category("카테고리")
                .content("This is english content.")
                .koContent("이것은 한글 내용입니다.")
                .author(createMemberDto())
                .wordHints(Set.of(createWordHintDto()))
                .repliesCount(2)
                .build();
    }

    public static ReplyDto.ReadOnly createReplyDto() {
        return ReplyDto.ReadOnly.builder()
                .questionId(QUESTION_ID)
                .replyId(REPLY_ID)
                .author(createMemberDto())
                .question(createQuestionDto())
                .content("Test Reply")
                .image("image-path")
                .replyLikeCount(2)
                .requestedByAuthor(true)
                .likedByRequester(true)
                .build();
    }

    public static ReplyLikesDto createReplyLikesDto(){
        return ReplyLikesDto.builder()
                .replyId(1L)
                .memberId(1L)
                .build();
    }

    public static WordHintDto.ReadOnly createWordHintDto() {
        return WordHintDto.ReadOnly.builder()
                .hintId(1L)
                .word("apple")
                .meaning("사과")
                .build();
    }

    public static TodayExpressionDto createTodayExpressionDto() {
        return TodayExpressionDto.builder()
                .expId(1L)
                .expression("test")
                .meaning("테스트")
                .expressionExample("test driven development")
                .expressionExampleMeaning("테스트 주도 개발")
                .scrappedByRequester(false)
                .build();
    }
}
