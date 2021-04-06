package net.mureng.mureng.domain.badge;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.mureng.mureng.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "BADGE_ACCOMPLISHED")
public class BadgeAccomplished {

    @Id
    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badgeId;

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
}

