package net.mureng.mureng.domain.question;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "member_id")
    private Long memberId;

    private String category;

    private String content;

    @Column(name = "ko_content")
    private String koContent;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

}
