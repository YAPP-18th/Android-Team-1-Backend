package net.mureng.core.common;

import net.mureng.core.member.entity.Member;
import net.mureng.core.question.entity.Question;
import net.mureng.core.question.entity.WordHint;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.entity.ReplyLikes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class EntityCreator {
    private static final Long QUESTION_ID = 1L;
    private static final Long MEMBER_ID = 1L;
    private static final Long REPLY_ID = 1L;

    public static Question createQuestionEntity(){
        return Question.builder()
                .questionId(QUESTION_ID)
                .author(EntityCreator.createMemberEntity())
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
                .image("test-image")
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

    public static WordHint createWordHint() {
        return  WordHint.builder()
                .hintId(1L)
                .question(Question.builder().build())
                .word("apple")
                .meaning("사과")
                .regDate(LocalDateTime.parse("2020-10-14T11:00:00"))
                .build();
    }
}
