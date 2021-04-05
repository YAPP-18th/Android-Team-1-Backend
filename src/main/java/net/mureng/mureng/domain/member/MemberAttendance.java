package net.mureng.mureng.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_ATTENDANCE")
public class MemberAttendance {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "attendance_count")
    private Long attendanceCount;

    @Column(name = "last_attendance_date")
    private LocalDate lastAttendanceDate;

}
