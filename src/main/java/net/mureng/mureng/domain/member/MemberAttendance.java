package net.mureng.mureng.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_ATTENDANCE")
public class MemberAttendance {

    @EmbeddedId
    private MemberAttendancePK id;

    @Column(name = "attendance_count", nullable = false)
    private Long attendanceCount;

    @Column(name = "last_attendance_date", nullable = false)
    private LocalDate lastAttendanceDate;

    @Builder
    public MemberAttendance(MemberAttendancePK id, Long attendanceCount, LocalDate lastAttendanceDate) {
        this.id = id;
        this.attendanceCount = attendanceCount;
        this.lastAttendanceDate = lastAttendanceDate;
    }
}
