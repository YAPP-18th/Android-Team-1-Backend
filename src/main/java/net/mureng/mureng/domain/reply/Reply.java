package net.mureng.mureng.domain.reply;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.mureng.mureng.domain.member.Member;
import net.mureng.mureng.domain.question.Question;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question questionId;

    private String content;

    private String image;

    private Boolean visible;

    private Boolean deleted;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "mod_date")
    private LocalDateTime modDate;

}
