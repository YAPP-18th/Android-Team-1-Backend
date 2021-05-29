package net.mureng.core.badge.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mureng.core.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
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

    @Builder.Default
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

}

