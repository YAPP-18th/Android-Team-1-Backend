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
    @Column(name = "is_push_active", nullable = false)
    private boolean isPushActive = true;
}
