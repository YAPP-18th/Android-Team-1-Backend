package net.mureng.mureng.question.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.question.entity.TodayQuestion;
import net.mureng.mureng.question.entity.WordHint;
import net.mureng.mureng.question.repository.TodayQuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@MurengDataTest
class TodayQuestionRepositoryTest {
    public static final Long MEMBER_ID = 1L;

    @Autowired
    private TodayQuestionRepository todayQuestionRepository;

    @Test
    @DatabaseSetup({
            "classpath:dbunit/entity/member.xml",
            "classpath:dbunit/entity/question.xml",
            "classpath:dbunit/entity/today_question.xml"
    })
    public void 오늘의_질문_테스트() {
        TodayQuestion todayQuestion = todayQuestionRepository.findById(MEMBER_ID).orElseThrow();

        assertEquals(MEMBER_ID, todayQuestion.getMemberId());
        assertEquals("Test", todayQuestion.getMember().getNickname());
        assertEquals("what is your favorite color?", todayQuestion.getQuestion().getContent());
    }

    @Test
    @DatabaseSetup({
            "classpath:dbunit/entity/member.xml",
            "classpath:dbunit/entity/question.xml",
            "classpath:dbunit/entity/today_question.xml",
            "classpath:dbunit/entity/word_hint.xml"
    })
    public void 오늘의_질문_연관_단어힌트_테스트() {
        TodayQuestion todayQuestion = todayQuestionRepository.findById(MEMBER_ID).orElseThrow();
        Set<WordHint> wordHints = todayQuestion.getQuestion().getWordHints();
        assertEquals(2, wordHints.size());
    }
}