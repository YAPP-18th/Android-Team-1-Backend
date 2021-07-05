package net.mureng.core.reply.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.core.annotation.MurengDataTest;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.reply.entity.Reply;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MurengDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/member.xml",
        "classpath:dbunit/entity/question.xml",
        "classpath:dbunit/entity/reply.xml",
        "classpath:dbunit/entity/reply_likes.xml"
})
public class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    private static final Long MEMBER_ID = 1L;
    private static final Long QUESTION_ID = 1L;
    private static final Long REPLY_ID = 1L;

    @Test
    public void 멤버_답변_목록_조회_내림차순(){
        List<Reply> replyList = replyRepository.findAllByAuthorMemberIdOrderByReplyIdDesc(MEMBER_ID);

        assertEquals(2, replyList.size());

        assertEquals(MEMBER_ID, replyList.get(0).getAuthor().getMemberId());
        assertEquals("so cute", replyList.get(0).getContent());
        assertEquals("cat image", replyList.get(0).getImage());

        assertEquals(MEMBER_ID, replyList.get(1).getAuthor().getMemberId());
        assertEquals("yellow", replyList.get(1).getContent());
        assertEquals("yellow image", replyList.get(1).getImage());
    }

    @Test
    public void 질문_답변_목록_조회(){
        List<Reply> replyList = replyRepository.findAllByQuestionQuestionId(QUESTION_ID);

        assertEquals(3, replyList.size());

        assertEquals(QUESTION_ID, replyList.get(0).getQuestion().getQuestionId());
        assertEquals("yellow", replyList.get(0).getContent());
        assertEquals("yellow image", replyList.get(0).getImage());

        assertEquals(QUESTION_ID, replyList.get(1).getQuestion().getQuestionId());
        assertEquals("red", replyList.get(1).getContent());
        assertEquals("red image", replyList.get(1).getImage());
    }

    @Test
    public void 질문_답변_목록_조회_페이징(){
        List<Reply> replyList = replyRepository.findAllByQuestionQuestionId(
                QUESTION_ID, PageRequest.of(0, 1)).getContent();

        assertEquals(1, replyList.size());

        assertEquals(QUESTION_ID, replyList.get(0).getQuestion().getQuestionId());
        assertEquals("yellow", replyList.get(0).getContent());
        assertEquals("yellow image", replyList.get(0).getImage());
    }

    @Test
    public void 이미_답변한_멤버_존재여부_테스트(){
        LocalDate date = LocalDate.of(2020,10,14);
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.of(0,0,0));
        LocalDateTime endDateTime = LocalDateTime.of(date, LocalTime.of(23,59,59));

        LocalDate date2 = LocalDate.of(2020,10,16);
        LocalDateTime startDateTime2 = LocalDateTime.of(date2, LocalTime.of(0,0,0));
        LocalDateTime endDateTime2 = LocalDateTime.of(date2, LocalTime.of(23,59,59));

        boolean isExist = replyRepository.existsByRegDateBetweenAndAuthorMemberId(startDateTime, endDateTime, MEMBER_ID);
        boolean isExist2 = replyRepository.existsByRegDateBetweenAndAuthorMemberId(startDateTime2, endDateTime2, MEMBER_ID);

        assertTrue(isExist);
        assertFalse(isExist2);
    }

    @Test
    public void 멤버와_질문으로_답변_찾기_테스트() {
        Reply oldReply = replyRepository.findByAuthorMemberIdAndQuestionQuestionId(MEMBER_ID, QUESTION_ID).orElseThrow();

        assertEquals(REPLY_ID, oldReply.getReplyId());
        assertEquals(MEMBER_ID, oldReply.getAuthor().getMemberId());
        assertEquals(QUESTION_ID, oldReply.getQuestion().getQuestionId());
        assertEquals("yellow", oldReply.getContent());
        assertEquals("yellow image", oldReply.getImage());
    }

    @Test
    public void 답변_삭제_테스트(){
        replyRepository.deleteById(REPLY_ID);

        assertFalse(replyRepository.existsById(REPLY_ID));
    }

    @Test
    public void 질문_연관_답변_조회_최신순_테스트() {
        List<Reply> replies = replyRepository.findAllByQuestionQuestionId(QUESTION_ID, PageRequest.of(0,
                2, Sort.by(Sort.Direction.DESC, "regDate"))).getContent();

        assertEquals(2, replies.size());
        assertEquals(4L, replies.get(0).getReplyId());
        assertEquals(2L, replies.get(1).getReplyId());
    }

    @Test
    public void 질문_연관_답변_조회_인기순_테스트() {
        Page<Reply> replies = replyRepository.findAllByQuestionQuestionIdOrderByReplyLikesSize(QUESTION_ID, PageRequest.of(0,
                2));

        assertEquals(2, replies.getNumberOfElements());
        assertEquals(3, replies.getTotalElements());
        assertEquals(2, replies.getTotalPages());
        assertEquals(2, replies.getContent().size());
        assertEquals(2, replies.getContent().get(0).getReplyLikes().size());
        assertEquals(0, replies.getContent().get(1).getReplyLikes().size());
    }

    @Test
    public void 질문_회원_중복_답변_예외_테스트() {
        // MEMBER_ID = 1, QUESTION_ID = 1이고, 이는 이미 테이블에 들어가 있다.
        Reply reply = EntityCreator.createReplyEntity();
        reply.setReplyId(null);

        assertThrows(DataIntegrityViolationException.class, () -> replyRepository.saveAndFlush(reply));
    }
}
