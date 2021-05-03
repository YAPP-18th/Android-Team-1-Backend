package net.mureng.mureng.member.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEMBER_SETTING")
public class MemberSetting {

    @Id
    private Long memberId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "daily_end_time", nullable = false)
    private LocalTime dailyEndTime;

    @Builder.Default
    @Column(name = "is_push_active", nullable = false)
    private boolean isPushActive = true;
}
