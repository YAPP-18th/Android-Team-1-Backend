package net.mureng.mureng.question.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.member.repository.MemberRepository;
import net.mureng.mureng.question.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MurengDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/member.xml",
        "classpath:dbunit/entity/question.xml",
        "classpath:dbunit/entity/word_hint.xml"
})
public class QuestionRepositoryTest {

    private static final Long MEMBER_ID = 1L;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 전체_질문_목록_조회(){
        List<Question> questions = questionRepository.findAll();

        assertEquals(3, questions.size());

        Question question = questions.get(0);

        assertEquals(MEMBER_ID, question.getMember().getMemberId());
        assertEquals("what is your favorite color?", question.getContent());
        assertEquals("당신이 가장 좋아하는 색은 무엇인가요?", question.getKoContent());
    }

    @Test
    public void 멤버로_질문_목록_조회(){
        List<Question> questions = questionRepository.findAllByMemberMemberId(MEMBER_ID);

        assertEquals(2, questions.size());

        assertEquals(MEMBER_ID, questions.get(0).getMember().getMemberId());
        assertEquals("what is your favorite color?", questions.get(0).getContent());
        assertEquals("당신이 가장 좋아하는 색은 무엇인가요?", questions.get(0).getKoContent());

        assertEquals(MEMBER_ID, questions.get(1).getMember().getMemberId());
        assertEquals("How did you feel when you first heard the mureng?", questions.get(1).getContent());
        assertEquals("머렝을 처음 들었을 때 어떤 느낌이 들었나요?", questions.get(1).getKoContent());
    }

    @Test
    public void 이미_답변한_질문인지_테스트(){
        long alreadyRepliedMemberId = 1;
        long notRepliedMemberId = 3;

        boolean isExist = questionRepository.existsByQuestionIdAndMemberMemberId(1L, alreadyRepliedMemberId);
        boolean isExist2 = questionRepository.existsByQuestionIdAndMemberMemberId(1L, notRepliedMemberId);

        assertTrue(isExist);
        assertFalse(isExist2);
    }
}
