package net.mureng.mureng.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mureng.mureng.domain.todayExpression.TodayExpression;

import javax.persistence.*;
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
     * 보통 예제에선 부모, 자식, 자손 관계로 표현 되어 있어서
     * 복합키 설정을 알맞게 한 건지 잘 모르겠음.
     **/
    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("expId")
    @ManyToOne
    @JoinColumn(name = "exp_id")
    private TodayExpression todayExpression;

    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;

}
