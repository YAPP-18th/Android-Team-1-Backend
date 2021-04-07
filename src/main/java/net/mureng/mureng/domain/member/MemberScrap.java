package net.mureng.mureng.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_SCRAP")
public class MemberScrap {
    @EmbeddedId
    private MemberScrapPK id;

    /**
     * 주석처리 안할 시, "attempted to assign id from null one-to-one property" 에러 발생
     **/
//    @MapsId("memberId")
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @MapsId("expId")
//    @ManyToOne
//    @JoinColumn(name = "exp_id")
//    private TodayExpression todayExpression;

    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;

    @Builder
    public MemberScrap(MemberScrapPK id, LocalDate regDate) {
        this.id = id;
        this.regDate = regDate;
    }
}
