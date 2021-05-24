package net.mureng.core.todayexpression.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.entity.MemberScrap;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "today_expression")
public class TodayExpression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id", nullable = false)
    private Long expId;

    @Column(nullable = false)
    private String expression;

    @Column(nullable = false)
    private String meaning;

    @Column(name = "expression_example")
    private String expressionExample;

    @Column(name = "expression_example_meaning")
    private String expressionExampleMeaning;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate = LocalDateTime.now();

    @Builder.Default
    @OneToMany(mappedBy = "todayExpression", cascade = CascadeType.ALL)
    private Set<MemberScrap> memberScraps = new HashSet<>();

    @Builder
    public TodayExpression(Long expId, String expression, String meaning, String expressionExample, String expressionExampleMeaning, LocalDateTime regDate, LocalDateTime modDate) {
        this.expId = expId;
        this.expression = expression;
        this.meaning = meaning;
        this.expressionExample = expressionExample;
        this.expressionExampleMeaning = expressionExampleMeaning;
        this.regDate = regDate;
        this.modDate = modDate;
    }

    public boolean scrappedByMember(Member member){
        for(MemberScrap memberScrap : this.memberScraps){
            if(memberScrap.getId().getMemberId() == member.getMemberId())
                return true;
        }
        return false;
    }
}
