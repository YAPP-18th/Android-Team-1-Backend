package net.mureng.core.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mureng.core.todayexpression.entity.TodayExpression;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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
    private TodayExpression todayExpression;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Builder
    public MemberScrap(MemberScrapPK id, LocalDateTime regDate) {
        this.id = id;
        this.regDate = regDate;
    }
}
