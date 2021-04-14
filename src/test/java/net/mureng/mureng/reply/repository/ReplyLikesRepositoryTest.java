package net.mureng.mureng.reply.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.reply.repository.ReplyLikesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MurengDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/member.xml",
        "classpath:dbunit/entity/question.xml",
        "classpath:dbunit/entity/reply.xml",
        "classpath:dbunit/entity/reply_likes.xml"
})
public class ReplyLikesRepositoryTest {
    @Autowired
    ReplyLikesRepository replyLikesRepository;

    private static final Long REPLY_ID = 1L;

    @Test
    public void 답변_좋아요_수_조회(){
        int replyCount = replyLikesRepository.countByIdReplyId(REPLY_ID);

        assertEquals(2, replyCount);
    }
}
