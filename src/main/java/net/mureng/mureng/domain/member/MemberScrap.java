package net.mureng.mureng.domain.member;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.mureng.mureng.domain.todayExpression.TodayExpression;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_SCRAP")
public class MemberScrap {
    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "exp_id")
    private TodayExpression expId;

    @Column(name = "reg_date")
    private LocalDate regDate;


}
