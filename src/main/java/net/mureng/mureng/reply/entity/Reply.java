package net.mureng.mureng.reply.entity;

import lombok.*;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @Builder.Default
    @Column(nullable = false)
    private Boolean visible = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

    @Builder.Default
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate = LocalDateTime.now();

    @Builder.Default
    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL)
    private Set<ReplyLikes> replyLikes = new HashSet<>();

    public void modifyReply(Reply newReply) {
        this.content = newReply.getContent();
        this.image= newReply.getImage();
        this.modDate = LocalDateTime.now();
    }

    public boolean isWriter(Member member){
        return this.author.getMemberId() == member.getMemberId();
    }

}
