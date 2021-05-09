package net.mureng.mureng.reply.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mureng.mureng.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "reply_likes")
public class ReplyLikes {
    @EmbeddedId
    private ReplyLikesPK id;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("replyId")
    @ManyToOne
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Builder
    public ReplyLikes(ReplyLikesPK id, LocalDateTime regDate) {
        this.id = id;
        this.regDate = regDate;
    }
}
