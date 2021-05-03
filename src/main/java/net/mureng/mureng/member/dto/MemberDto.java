package net.mureng.mureng.member.dto;

import lombok.*;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.entity.MemberSetting;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MemberDto {
    private Long memberId;

    private String identifier;

    private String email;

    private boolean isActive;

    private String nickname;

    private String image;

    private long murengCount = 0L;

    private long attendanceCount;

    private LocalDate lastAttendanceDate;

    private LocalTime dailyEndTime;

    private boolean isPushActive;
}
