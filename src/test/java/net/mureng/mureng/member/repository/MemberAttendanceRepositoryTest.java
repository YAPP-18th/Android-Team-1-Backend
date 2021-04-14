package net.mureng.mureng.member.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.repository.MemberAttendanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@MurengDataTest
class MemberAttendanceRepositoryTest {
    @Autowired
    private MemberAttendanceRepository memberAttendanceRepository;

    @Test
    @DatabaseSetup({
            "classpath:dbunit/entity/member.xml",
            "classpath:dbunit/entity/member_attendance.xml"
    })
    public void 회원_출석_조회_연관_테스트() {
        MemberAttendance memberAttendance = memberAttendanceRepository.findById(1L).orElseThrow();
        Member member = memberAttendance.getMember();

        assertThat(memberAttendance.getMemberId(), is(equalTo(1L)));
        assertThat(member.getMemberId(), is(equalTo(1L)));
        assertThat(member.getIdentifier(), is(equalTo("123")));
    }
}