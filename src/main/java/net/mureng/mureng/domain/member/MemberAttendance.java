package net.mureng.mureng.domain.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_ATTENDANCE")
public class MemberAttendance {

    @Id
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "attendance_count")
    private Long attendanceCount;

    @Column(name = "last_attendance_date")
    private LocalDate lastAttendanceDate;

}
