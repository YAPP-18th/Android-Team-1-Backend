package net.mureng.mureng.domain.member;

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


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTest {
    
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TodayExpressionRepository todayExpressionRepository;

    @Autowired
    MemberScrapRepository memberScrapRepository;

    @Test
    public void 멤버_회원가입(){
        String email = "test@gmail.com";

        memberRepository.save(Member.builder()
                                    .identifier("123")
                                    .email(email)
                                    .isActive(true)
                                    .nickname("Test")
                                    .regDate(LocalDateTime.now())
                                    .modDate(LocalDateTime.now())
                                    .murengCount(new Long(0))
                                    .build());

        List<Member> memberList = memberRepository.findAll();

        Member member = memberList.get(0);
        assertThat(member.getEmail(), is(equalTo(email)));

    }


    @Test
    public void 멤버_스크랩(){
        // Member signup
        String email = "test@gmail.com";

        memberRepository.save(Member.builder()
                .identifier("123")
                .email(email)
                .isActive(true)
                .nickname("Test")
                .regDate(LocalDateTime.now())
                .modDate(LocalDateTime.now())
                .murengCount(new Long(0))
                .build());

        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);

        // Make TodayExpression
        todayExpressionRepository.save(TodayExpression.builder()
                .expression("test")
                .meaning("테스트")
                .regDate(LocalDateTime.now())
                .modDate(LocalDateTime.now())
                .build());

        List<TodayExpression> todayExpressionList = todayExpressionRepository.findAll();
        TodayExpression todayExpression = todayExpressionList.get(0);


        Long memberId = member.getMemberId();
        Long expId = todayExpression.getExpId();


        // Make MemberScrapPk
        MemberScrapPK pk = MemberScrapPK.builder()
                                        .memberId(memberId)
                                        .expId(expId)
                                        .build();

        memberScrapRepository.save(MemberScrap.builder()
                                            .id(pk)
                                            .regDate(LocalDate.now())
                                            .build());

        Optional<MemberScrap> memberScrap = memberScrapRepository.findById(pk);

        assertThat(todayExpression.getExpression(), is(equalTo("test")));
        assertThat(memberScrap.get().getId().getMemberId(), is(equalTo(member.getMemberId())));

    }
}
