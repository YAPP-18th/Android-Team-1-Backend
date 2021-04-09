package net.mureng.mureng.domain.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mureng.mureng.domain.badge.Badge;
import net.mureng.mureng.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "REPLY_LIKES")
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
    private LocalDateTime regDate;

    @Builder
    public ReplyLikes(ReplyLikesPK id, LocalDateTime regDate) {
        this.id = id;
        this.regDate = regDate;
    }
}
