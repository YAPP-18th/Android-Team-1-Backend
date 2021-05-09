package net.mureng.mureng.member.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Embeddable
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSetting {
    @Builder.Default
    @Column(name = "is_push_active", nullable = false)
    private boolean isPushActive = true;
}
