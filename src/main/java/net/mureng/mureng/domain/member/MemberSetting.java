package net.mureng.mureng.domain.member;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_SETTING")
public class MemberSetting {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "daily_end_time")
    private LocalTime dailyEndTime;

    @Column(name = "isPushActive")
    private Boolean isPushActive;
}
