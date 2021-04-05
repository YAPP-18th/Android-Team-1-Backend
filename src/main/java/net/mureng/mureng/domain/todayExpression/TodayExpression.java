package net.mureng.mureng.domain.todayExpression;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TODAY_EXPRESSION")
public class TodayExpression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id")
    private Long expId;

    private String expression;

    private String meaning;

    @Column(name = "expression_example")
    private String expressionExample;

    @Column(name = "expression_meaning")
    private String expressionMeaning;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "mod_date")
    private LocalDateTime modDate;
}
