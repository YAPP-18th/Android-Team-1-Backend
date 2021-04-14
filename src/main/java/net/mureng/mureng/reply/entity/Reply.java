package net.mureng.mureng.reply.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
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

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean visible;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

    @Builder
    public Reply(Long replyId, Member memberId, Question questionId, String content, String image, Boolean visible, Boolean deleted, LocalDateTime regDate, LocalDateTime modDate) {
        this.replyId = replyId;
        this.memberId = memberId;
        this.questionId = questionId;
        this.content = content;
        this.image = image;
        this.visible = visible;
        this.deleted = deleted;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
