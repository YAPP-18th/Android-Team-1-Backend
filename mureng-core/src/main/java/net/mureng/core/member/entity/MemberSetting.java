package net.mureng.core.member.entity;


import lombok.*;

import javax.persistence.*;

@Embeddable
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSetting {
    @Builder.Default
    @Column(name = "is_daily_push_active", nullable = false)
    private boolean isDailyPushActive = true;

    @Builder.Default
    @Column(name = "is_like_push_active", nullable = false)
    private boolean isLikePushActive = true;
}
