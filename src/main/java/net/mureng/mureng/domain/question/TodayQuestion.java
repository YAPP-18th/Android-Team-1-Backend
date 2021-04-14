package net.mureng.mureng.domain.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mureng.mureng.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TODAY_QUESTION")
public class TodayQuestion {

    @Id
    private Long memberId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate = LocalDateTime.now();

    @Builder
    public TodayQuestion(Long memberId, Member member, Question question, LocalDateTime regDate, LocalDateTime modDate) {
        this.memberId = memberId;
        this.member = member;
        this.question = question;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
