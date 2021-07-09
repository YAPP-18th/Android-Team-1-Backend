package net.mureng.api.member.component;

import net.mureng.api.member.dto.MemberDto;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.entity.MemberAttendance;
import net.mureng.core.member.entity.MemberSetting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
                                        .memberAttendance(MemberAttendance.builder()
                                                .attendanceCount(10)
                                                .lastAttendanceDate(LocalDate.of(2020, 10, 14))
                                                .build())
                                        .memberSetting(MemberSetting.builder()
                                                .isLikePushActive(true)
                                                .isDailyPushActive(false)
                                                .build())
                                        .murengCookies(Set.of())
                                        .build();

    private final MemberDto.ReadOnly memberDto = MemberDto.ReadOnly.builder()
                                        .memberId(1L)
                                        .identifier("123")
                                        .email("test@gmail.com")
                                        .nickname("Test")
                                        .murengCount(0)
                                        .attendanceCount(10)
                                        .lastAttendanceDate("2020-10-14")
                                        .memberSetting(MemberSetting.builder()
                                                .isLikePushActive(true)
                                                .isDailyPushActive(false)
                                                .build())
                                        .build();

    @Test
    void 엔티티에서_DTO변환_테스트() {
        MemberDto.ReadOnly mappedDto = memberMapper.toDto(member);
        assertEquals(mappedDto.getMemberId(), memberDto.getMemberId());
        assertEquals(mappedDto.getIdentifier(), memberDto.getIdentifier());
        assertEquals(mappedDto.getImage(), memberDto.getImage());
        assertEquals(mappedDto.getEmail(), memberDto.getEmail());
        assertEquals(mappedDto.getNickname(), memberDto.getNickname());
        assertEquals(mappedDto.getMurengCount(), memberDto.getMurengCount());
        assertEquals(mappedDto.getAttendanceCount(), memberDto.getAttendanceCount());
        assertEquals(mappedDto.getLastAttendanceDate(), memberDto.getLastAttendanceDate());
        assertEquals(mappedDto.getMemberSetting().isLikePushActive(), memberDto.getMemberSetting().isLikePushActive());
        assertEquals(mappedDto.getMemberSetting().isDailyPushActive(), memberDto.getMemberSetting().isDailyPushActive());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        Member mappedMember = memberMapper.toEntity(memberDto);
//        assertEquals(mappedMember.getMemberId(), member.getMemberId());
        assertEquals(mappedMember.getIdentifier(), member.getIdentifier());
        assertEquals(mappedMember.getEmail(), member.getEmail());
//        assertEquals(mappedMember.isActive(), member.isActive());
        assertEquals(mappedMember.getImage(), member.getImage());
        assertEquals(mappedMember.getNickname(), member.getNickname());
//        assertEquals(mappedMember.getMurengCount(), member.getMurengCount());
//        assertEquals(mappedMember.getMemberAttendance().getAttendanceCount(), member.getMemberAttendance().getAttendanceCount());
//        assertEquals(mappedMember.getMemberAttendance().getLastAttendanceDate(), member.getMemberAttendance().getLastAttendanceDate());
//        assertEquals(mappedMember.getMemberSetting().isPushActive(), member.getMemberSetting().isPushActive());
    }
}