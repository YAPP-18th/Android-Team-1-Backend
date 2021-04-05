package net.mureng.mureng.domain.badge;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "BADGE_ACCOMPLISHED")
public class BadgeAccomplished {

    @Id
    @Column(name = "badge_id")
    private Badge badgeId;

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
}

