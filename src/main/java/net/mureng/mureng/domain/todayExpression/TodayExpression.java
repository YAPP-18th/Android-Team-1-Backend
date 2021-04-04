package net.mureng.mureng.domain.todayExpression;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class TodayExpression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exp_id;

    private String expression;

    private String meaning;

    private String expression_example;

    private String expression_meaning;

    private LocalDateTime reg_date;

    private LocalDateTime mod_date;
}
