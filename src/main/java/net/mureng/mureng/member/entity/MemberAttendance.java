package net.mureng.mureng.member.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private long attendanceCount;

    @Column(name = "last_attendance_date", nullable = false)
    private LocalDate lastAttendanceDate;
}
