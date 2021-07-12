package net.mureng.core.member.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.core.annotation.MurengDataTest;
import net.mureng.core.member.entity.MemberScrap;
import net.mureng.core.member.entity.MemberScrapPK;
import net.mureng.core.todayexpression.entity.UsefulExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@MurengDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/member.xml",
        "classpath:dbunit/entity/useful_expression.xml",
        "classpath:dbunit/entity/member_scrap.xml"
})
public class MemberScrapRepositoryTest {

    private static final Long MEMBER_ID = 1L;

    @Autowired
    MemberScrapRepository memberScrapRepository;

    @Test
    public void 멤버_스크랩_조회_테스트(){
        // Make MemberScrapPk
        MemberScrapPK pk = MemberScrapPK.builder()
                                        .memberId(MEMBER_ID)
                                        .expId(1L)
                                        .build();

        MemberScrap memberScrap = memberScrapRepository.findById(pk).orElseThrow();

        assertEquals(2020, memberScrap.getRegDate().getYear());
        assertEquals(10, memberScrap.getRegDate().getMonthValue());
        assertEquals(14, memberScrap.getRegDate().getDayOfMonth());
        assertEquals(1L, memberScrap.getId().getExpId());
    }

    @Test
    public void 멤버로_스크랩목록_조회_테스트(){
        List<MemberScrap> memberScraps = memberScrapRepository.findAllByIdMemberId(MEMBER_ID);

        assertEquals(2, memberScraps.size());

        assertEquals(2020, memberScraps.get(0).getRegDate().getYear());
        assertEquals(10, memberScraps.get(0).getRegDate().getMonthValue());
        assertEquals(14, memberScraps.get(0).getRegDate().getDayOfMonth());
        assertEquals(1L, memberScraps.get(0).getId().getExpId());

        assertEquals(2020, memberScraps.get(1).getRegDate().getYear());
        assertEquals(10, memberScraps.get(1).getRegDate().getMonthValue());
        assertEquals(15, memberScraps.get(1).getRegDate().getDayOfMonth());
        assertEquals(2L, memberScraps.get(1).getId().getExpId());
    }

    @Test
    public void 멤버로_스크랩목록_내용조회_테스트(){
        List<UsefulExpression> usefulExpressions = memberScrapRepository.findAllByIdMemberId(MEMBER_ID).stream()
                .map(MemberScrap::getUsefulExpression)
                .collect(Collectors.toList());

        assertEquals(2, usefulExpressions.size());

        assertEquals("test", usefulExpressions.get(0).getExpression());
        assertEquals("테스트", usefulExpressions.get(0).getMeaning());
        assertEquals("this is test.", usefulExpressions.get(0).getExpressionExample());
        assertEquals("이것은 테스트이다.", usefulExpressions.get(0).getExpressionExampleMeaning());

        assertEquals("test2", usefulExpressions.get(1).getExpression());
        assertEquals("테스트2", usefulExpressions.get(1).getMeaning());
        assertEquals("this is test2.", usefulExpressions.get(1).getExpressionExample());
        assertEquals("이것은 테스트2이다.", usefulExpressions.get(1).getExpressionExampleMeaning());
    }
}
