package net.mureng.mureng.domain.reply;

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
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reply_id;

    private Long member_id;

    private Long question_id;

    private String content;

    private String image;

    private Boolean visible;

    private Boolean deleted;

    private LocalDateTime reg_date;

    private LocalDateTime mod_date;

}
