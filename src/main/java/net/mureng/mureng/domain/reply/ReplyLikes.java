package net.mureng.mureng.domain.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "REPLY_LIKES")
public class ReplyLikes {
    @EmbeddedId
    private ReplyLikesPK id;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Builder
    public ReplyLikes(ReplyLikesPK id, LocalDateTime regDate) {
        this.id = id;
        this.regDate = regDate;
    }
}
