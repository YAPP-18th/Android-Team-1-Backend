package net.mureng.mureng.member.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.repository.MemberAttendanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

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

        assertEquals(1L, (long)memberAttendance.getMemberId());
        assertEquals(10, memberAttendance.getAttendanceCount());
        assertEquals(LocalDate.of(2020, 10, 14), memberAttendance.getLastAttendanceDate());
    }
}