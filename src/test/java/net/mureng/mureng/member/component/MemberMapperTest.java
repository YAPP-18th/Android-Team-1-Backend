package net.mureng.mureng.member.component;

import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.entity.MemberSetting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {
    @Autowired
    private MemberMapper memberMapper;

    private final Member member = Member.builder()
                                        .memberId(1L)
                                        .identifier("123")
                                        .email("test@gmail.com")
                                        .isActive(true)
                                        .nickname("Test")
                                        .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                                        .modDate(LocalDateTime.of(2020, 10, 14, 17, 11, 10))
                                        .murengCount(0)
                                        .memberAttendance(MemberAttendance.builder()
                                                .memberId(1L)
                                                .attendanceCount(10L)
                                                .lastAttendanceDate(LocalDate.of(2020, 10, 14))
                                                .build())
                                        .memberSetting(MemberSetting.builder()
                                                .memberId(1L)
                                                .isPushActive(true)
                                                .dailyEndTime(LocalTime.of(11,0,0))
                                                .build())
                                        .build();

    private final MemberDto memberDto = MemberDto.builder()
                                        .memberId(1L)
                                        .identifier("123")
                                        .email("test@gmail.com")
                                        .nickname("Test")
                                        .murengCount(0)
                                        .attendanceCount(10)
                                        .lastAttendanceDate("2020-10-14")
                                        .isPushActive(true)
                                        .dailyEndTime("11:00:00")
                                        .build();

    @Test
    void 엔티티에서_DTO변환_테스트() {
        MemberDto mappedDto = memberMapper.map(member);
        assertEquals(mappedDto.getMemberId(), memberDto.getMemberId());
        assertEquals(mappedDto.getIdentifier(), memberDto.getIdentifier());
        assertEquals(mappedDto.getEmail(), memberDto.getEmail());
        assertEquals(mappedDto.getNickname(), memberDto.getNickname());
        assertEquals(mappedDto.getMurengCount(), memberDto.getMurengCount());
        assertEquals(mappedDto.getAttendanceCount(), memberDto.getAttendanceCount());
        assertEquals(mappedDto.getLastAttendanceDate(), memberDto.getLastAttendanceDate());
        assertEquals(mappedDto.isPushActive(), memberDto.isPushActive());
        assertEquals(mappedDto.getDailyEndTime(), memberDto.getDailyEndTime());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        Member mappedMember = memberMapper.map(memberDto);
        assertEquals(mappedMember.getMemberId(), member.getMemberId());
        assertEquals(mappedMember.getIdentifier(), member.getIdentifier());
        assertEquals(mappedMember.getEmail(), member.getEmail());
        assertEquals(mappedMember.isActive(), member.isActive());
        assertEquals(mappedMember.getNickname(), member.getNickname());
        assertEquals(mappedMember.getMurengCount(), member.getMurengCount());
        assertEquals(mappedMember.getMemberAttendance().getAttendanceCount(), member.getMemberAttendance().getAttendanceCount());
        assertEquals(mappedMember.getMemberAttendance().getLastAttendanceDate(), member.getMemberAttendance().getLastAttendanceDate());
        assertEquals(mappedMember.getMemberSetting().getDailyEndTime(), member.getMemberSetting().getDailyEndTime());
        assertEquals(mappedMember.getMemberSetting().isPushActive(), member.getMemberSetting().isPushActive());
    }
}