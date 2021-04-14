package net.mureng.mureng.badge.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mureng.mureng.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "BADGE_ACCOMPLISHED")
public class BadgeAccomplished {

    @EmbeddedId
    private BadgeAccomplishedPK id;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("badgeId")
    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Builder
    public BadgeAccomplished(BadgeAccomplishedPK id, LocalDateTime regDate) {
        this.id = id;
        this.regDate = regDate;
    }
}

