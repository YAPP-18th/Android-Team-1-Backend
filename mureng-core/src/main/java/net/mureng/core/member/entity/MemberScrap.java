package net.mureng.core.member.entity;

import lombok.*;
import net.mureng.core.todayexpression.entity.UsefulExpression;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_scrap")
public class MemberScrap {
    @EmbeddedId
    private MemberScrapPK id;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("expId")
    @ManyToOne
    @JoinColumn(name = "exp_id")
    private UsefulExpression usefulExpression;

    @Builder.Default
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();
}
