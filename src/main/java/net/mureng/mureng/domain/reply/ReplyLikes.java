package net.mureng.mureng.domain.reply;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "REPLY_LIKES")
public class ReplyLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
