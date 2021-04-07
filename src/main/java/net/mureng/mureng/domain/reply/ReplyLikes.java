package net.mureng.mureng.domain.reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mureng.mureng.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "REPLY_LIKES")
public class ReplyLikes {

    @Id
    @ManyToOne
    @JoinColumn(name = "reply_id")
    private Reply replyId;

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;
}
