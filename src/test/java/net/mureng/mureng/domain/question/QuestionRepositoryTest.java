package net.mureng.mureng.domain.question;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

        assertEquals(MEMBER_ID, question.getMemberId().getMemberId());
        assertEquals("what is your favorite color?", question.getContent());
        assertEquals("당신이 가장 좋아하는 색은 무엇인가요?", question.getKoContent());
    }

    @Test
    public void 멤버로_질문_목록_조회(){
        List<Question> questions = questionRepository.findAllByMemberIdMemberId(MEMBER_ID);


        assertEquals(2, questions.size());

        assertEquals(MEMBER_ID, questions.get(0).getMemberId().getMemberId());
        assertEquals("what is your favorite color?", questions.get(0).getContent());
        assertEquals("당신이 가장 좋아하는 색은 무엇인가요?", questions.get(0).getKoContent());

        assertEquals(MEMBER_ID, questions.get(1).getMemberId().getMemberId());
        assertEquals("How did you feel when you first heard the mureng?", questions.get(1).getContent());
        assertEquals("머렝을 처음 들었을 때 어떤 느낌이 들었나요?", questions.get(1).getKoContent());


    }
}
