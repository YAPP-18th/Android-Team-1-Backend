package net.mureng.mureng.domain.member;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.domain.todayExpression.TodayExpression;
import net.mureng.mureng.domain.todayExpression.TodayExpressionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@MurengDataTest
public class MemberRepositoryTest {
    
    @Autowired
    MemberRepository memberRepository;

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/멤버_회원가입.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 멤버_회원가입(){
        String email = "test@gmail.com";

        memberRepository.save(Member.builder()
                                    .identifier("123")
                                    .email(email)
                                    .isActive(true)
                                    .nickname("Test")
                                    .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                                    .modDate(LocalDateTime.of(2020, 10, 14, 17, 11, 10))
                                    .murengCount(0L)
                                    .build());
    }
}
