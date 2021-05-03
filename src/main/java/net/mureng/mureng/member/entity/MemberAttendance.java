package net.mureng.mureng.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEMBER_ATTENDANCE")
public class MemberAttendance {

    @Id @GeneratedValue
    private Long memberId;

    @Column(name = "attendance_count", nullable = false)
    private long attendanceCount;

    @Column(name = "last_attendance_date", nullable = false)
    @Builder.Default
    private LocalDate lastAttendanceDate = LocalDate.now();
}
