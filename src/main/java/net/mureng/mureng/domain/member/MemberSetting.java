package net.mureng.mureng.domain.member;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_SETTING")
public class MemberSetting {

    @Id
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "daily_end_time")
    private LocalTime dailyEndTime;

    @Column(name = "isPushActive")
    private Boolean isPushActive;
}
