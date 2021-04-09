package net.mureng.mureng.domain.member;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

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