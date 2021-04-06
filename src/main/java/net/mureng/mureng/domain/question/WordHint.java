package net.mureng.mureng.domain.question;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
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

    private String word;

    private String meaning;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
