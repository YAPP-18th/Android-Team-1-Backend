package net.mureng.mureng.question.entity;

import lombok.*;
import net.mureng.mureng.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 20)
    private String category;

    @Column(nullable = false, length = 150)
    private String content;

    @Column(name = "ko_content", nullable = false)
    private String koContent;

    @Builder.Default
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();
}
