package net.mureng.mureng.domain.question;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.mureng.mureng.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
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

    private String category;

    private String content;

    @Column(name = "ko_content")
    private String koContent;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

}
