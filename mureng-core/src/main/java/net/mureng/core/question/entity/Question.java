package net.mureng.core.question.entity;

import lombok.*;
import net.mureng.core.member.entity.Member;
import net.mureng.core.reply.entity.Reply;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Member author;

    @Column(length = 20)
    private String category;

    @Column(nullable = false, length = 150)
    private String content;

    @Column(name = "ko_content")
    private String koContent;

    @Builder.Default
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private Set<WordHint> wordHints = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "question")
    private List<Reply> replies = new ArrayList<>();

    @Builder.Default
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();
}
