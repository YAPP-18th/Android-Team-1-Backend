package net.mureng.core.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAttendance {
    @Column(name = "attendance_count", nullable = false)
    private int attendanceCount;

    @Column(name = "last_attendance_date", nullable = false)
    @Builder.Default
    private LocalDate lastAttendanceDate = LocalDate.now();
}
