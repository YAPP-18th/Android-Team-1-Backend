package net.mureng.core.badge.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mureng.core.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "badge_accomplished")
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
    private LocalDateTime regDate = LocalDateTime.now();

    @Builder
    public BadgeAccomplished(BadgeAccomplishedPK id, LocalDateTime regDate) {
        this.id = id;
        this.regDate = regDate;
    }
}

