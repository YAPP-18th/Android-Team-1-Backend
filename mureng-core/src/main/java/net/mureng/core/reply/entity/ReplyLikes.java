package net.mureng.core.reply.entity;

import lombok.*;
import net.mureng.core.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder.Default
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

}
