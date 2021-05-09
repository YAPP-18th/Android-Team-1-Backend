package net.mureng.mureng.reply.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.reply.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MurengDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/member.xml",
        "classpath:dbunit/entity/question.xml",
        "classpath:dbunit/entity/reply.xml"
})
public class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    private static final Long MEMBER_ID = 1L;
    private static final Long QUESTION_ID = 1L;

    @Test
    public void 멤버_답변_목록_조회(){
        List<Reply> replyList = replyRepository.findAllByMemberMemberId(MEMBER_ID);

        assertEquals(2, replyList.size());

        assertEquals(MEMBER_ID, replyList.get(0).getMember().getMemberId());
        assertEquals("yellow", replyList.get(0).getContent());
        assertEquals("yellow image", replyList.get(0).getImage());

        assertEquals(MEMBER_ID, replyList.get(1).getMember().getMemberId());
        assertEquals("so cute", replyList.get(1).getContent());
        assertEquals("cat image", replyList.get(1).getImage());
    }

    @Test
    public void 질문_답변_목록_조회(){
        List<Reply> replyList = replyRepository.findAllByQuestionQuestionId(QUESTION_ID);

        assertEquals(2, replyList.size());

        assertEquals(QUESTION_ID, replyList.get(0).getQuestion().getQuestionId());
        assertEquals("yellow", replyList.get(0).getContent());
        assertEquals("yellow image", replyList.get(0).getImage());

        assertEquals(QUESTION_ID, replyList.get(1).getQuestion().getQuestionId());
        assertEquals("red", replyList.get(1).getContent());
        assertEquals("red image", replyList.get(1).getImage());
    }
}
