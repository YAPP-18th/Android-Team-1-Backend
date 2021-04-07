package net.mureng.mureng.domain.member;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_SETTING")
public class MemberSetting {

    @Id
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "daily_end_time", nullable = false)
    private LocalTime dailyEndTime;

    @Column(name = "isPushActive", nullable = false, columnDefinition = "boolean default true")
    private Boolean isPushActive;
}
