package net.mureng.mureng.domain.todayExpression;

import lombok.Builder;
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
    @Column(name = "exp_id", nullable = false)
    private Long expId;

    @Column(nullable = false)
    private String expression;

    @Column(nullable = false)
    private String meaning;

    private String expressionExample;

    private String expressionMeaning;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

    @Builder
    public TodayExpression(Long expId, String expression, String meaning, String expressionExample, String expressionMeaning, LocalDateTime regDate, LocalDateTime modDate) {
        this.expId = expId;
        this.expression = expression;
        this.meaning = meaning;
        this.expressionExample = expressionExample;
        this.expressionMeaning = expressionMeaning;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
