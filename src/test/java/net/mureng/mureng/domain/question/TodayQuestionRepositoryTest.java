package net.mureng.mureng.domain.question;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@MurengDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/member.xml",
        "classpath:dbunit/entity/question.xml",
        "classpath:dbunit/entity/today_question.xml"
})
class TodayQuestionRepositoryTest {
    public static final Long MEMBER_ID = 1L;

    @Autowired
    private TodayQuestionRepository todayQuestionRepository;

    @Test
    public void 오늘의_질문_테스트() {
        TodayQuestion todayQuestion = todayQuestionRepository.findById(MEMBER_ID).orElseThrow();

        assertEquals(MEMBER_ID, todayQuestion.getMemberId());
        assertEquals("Test", todayQuestion.getMember().getNickname());
        assertEquals("what is your favorite color?", todayQuestion.getQuestion().getContent());
    }
}