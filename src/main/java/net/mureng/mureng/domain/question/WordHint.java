package net.mureng.mureng.domain.question;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class WordHint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hint_id;

    private Long question_id;

    private String word;

    private String meaning;

    private LocalDateTime reg_date;

    private LocalDateTime mod_date;
}
