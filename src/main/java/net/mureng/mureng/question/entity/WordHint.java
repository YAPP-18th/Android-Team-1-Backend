package net.mureng.mureng.question.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "WORD_HINT")
public class WordHint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hint_id")
    private Long hintId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question questionId;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String meaning;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Builder
    public WordHint(Long hintId, Question questionId, String word, String meaning, LocalDateTime regDate) {
        this.hintId = hintId;
        this.questionId = questionId;
        this.word = word;
        this.meaning = meaning;
        this.regDate = regDate;
    }
}
