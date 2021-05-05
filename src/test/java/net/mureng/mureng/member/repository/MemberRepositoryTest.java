package net.mureng.mureng.member.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.entity.MemberSetting;
import net.mureng.mureng.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@MurengDataTest
public class MemberRepositoryTest {
    
    @Autowired
    MemberRepository memberRepository;

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/멤버_회원가입.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 멤버_회원가입(){ // TODO 연관 데이터 들어가는지 추가 검증
        Member member = Member.builder()
                .memberId(1L)
                .identifier("123")
                .email("test@gmail.com")
                .isActive(true)
                .nickname("Test")
                .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                .modDate(LocalDateTime.of(2020, 10, 14, 17, 11, 10))
                .murengCount(0)
                .build();

        memberRepository.save(member);
    }

    @Test
    @DatabaseSetup({
            "classpath:dbunit/entity/member.xml",
            "classpath:dbunit/entity/member_setting.xml"
    })
    public void 멤버_연관_설정_조회() {
        Member member = memberRepository.findById(1L).orElseThrow();
        MemberSetting memberSetting = member.getMemberSetting();

        assertEquals(1L, memberSetting.getMemberId());
        assertEquals(17, memberSetting.getDailyEndTime().getHour());
        assertEquals(11, memberSetting.getDailyEndTime().getMinute());
        assertEquals(9, memberSetting.getDailyEndTime().getSecond());
    }

    @Test
    @DatabaseSetup({
            "classpath:dbunit/entity/member.xml",
            "classpath:dbunit/entity/member_attendance.xml"
    })
    public void 멤버_연관_출석_조회() {
        Member member = memberRepository.findById(1L).orElseThrow();
        MemberAttendance memberAttendance = member.getMemberAttendance();

        assertEquals(1L, memberAttendance.getMemberId());
        assertEquals(10, memberAttendance.getAttendanceCount());
        assertEquals(2020, memberAttendance.getLastAttendanceDate().getYear());
        assertEquals(10, memberAttendance.getLastAttendanceDate().getMonthValue());
        assertEquals(14, memberAttendance.getLastAttendanceDate().getDayOfMonth());
    }

    @Test
    @DatabaseSetup({
            "classpath:dbunit/entity/member.xml"
    })
    public void 닉네임_존재_조회() {
        assertTrue(memberRepository.existsByNickname("Test"));
        assertFalse(memberRepository.existsByNickname("Non-exist"));
    }
}
