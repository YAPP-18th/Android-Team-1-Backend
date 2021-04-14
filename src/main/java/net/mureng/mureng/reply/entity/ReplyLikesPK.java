package net.mureng.mureng.reply.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class ReplyLikesPK implements Serializable {
    private Long replyId;
    private Long memberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplyLikesPK that = (ReplyLikesPK) o;
        return Objects.equals(replyId, that.replyId) && Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replyId, memberId);
    }

    @Builder

    public ReplyLikesPK(Long replyId, Long memberId) {
        this.replyId = replyId;
        this.memberId = memberId;
    }
}
