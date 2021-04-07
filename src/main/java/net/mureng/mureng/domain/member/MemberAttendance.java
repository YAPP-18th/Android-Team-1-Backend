package net.mureng.mureng.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_ATTENDANCE")
public class MemberAttendance {

    @Id
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "attendance_count", nullable = false)
    private Long attendanceCount;

    @Column(name = "last_attendance_date", nullable = false)
    private LocalDate lastAttendanceDate;

}
