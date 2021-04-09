package net.mureng.mureng.domain.member;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_SETTING")
public class MemberSetting {

    @EmbeddedId
    private MemberSettingPK memberId;

    @Column(name = "daily_end_time", nullable = false)
    private LocalTime dailyEndTime;

    @Column(name = "is_push_active", nullable = false, columnDefinition = "boolean default true")
    private Boolean isPushActive;

    @Builder
    public MemberSetting(MemberSettingPK memberId, LocalTime dailyEndTime, Boolean isPushActive) {
        this.memberId = memberId;
        this.dailyEndTime = dailyEndTime;
        this.isPushActive = isPushActive;
    }
}
