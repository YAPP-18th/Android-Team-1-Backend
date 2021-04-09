package net.mureng.mureng.domain.member;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.domain.todayExpression.TodayExpression;
import net.mureng.mureng.domain.todayExpression.TodayExpressionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@MurengDataTest
public class MemberScrapRepositoryTest {

    @Autowired
    MemberScrapRepository memberScrapRepository;

    @Test
    @DatabaseSetup({
            "classpath:dbunit/entity/member.xml",
            "classpath:dbunit/entity/member_scrap.xml",
            "classpath:dbunit/entity/today_expression.xml"
    })
    public void 멤버_스크랩_조회_테스트(){
        // Make MemberScrapPk
        MemberScrapPK pk = MemberScrapPK.builder()
                                        .memberId(1L)
                                        .expId(1L)
                                        .build();


        MemberScrap memberScrap = memberScrapRepository.findById(pk).orElseThrow();

        assertThat(memberScrap.getRegDate().getYear(), is(equalTo(2020)));
        assertThat(memberScrap.getRegDate().getMonthValue(), is(equalTo(10)));
        assertThat(memberScrap.getRegDate().getDayOfMonth(), is(equalTo(14)));
        assertThat(memberScrap.getId().getMemberId(), is(equalTo(1L)));
    }
}
