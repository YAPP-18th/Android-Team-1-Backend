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

    @Id
    private Long memberId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "attendance_count", nullable = false)
    private Long attendanceCount;

    @Column(name = "last_attendance_date", nullable = false)
    private LocalDate lastAttendanceDate;

    @Builder
    public MemberAttendance(Long memberId, Long attendanceCount, LocalDate lastAttendanceDate) {
        this.memberId = memberId;
        this.attendanceCount = attendanceCount;
        this.lastAttendanceDate = lastAttendanceDate;
    }
}
