package net.mureng.mureng.domain.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
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
}
