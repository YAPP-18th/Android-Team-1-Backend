package net.mureng.mureng.domain.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mureng.mureng.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(length = 20)
    private String category;

    @Column(nullable = false, length = 150)
    private String content;

    @Column(name = "ko_content", nullable = false)
    private String koContent;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

}
