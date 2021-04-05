package net.mureng.mureng.domain.question;

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

    @Column(name = "question_id")
    private Long questionId;

    private String word;

    private String meaning;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
