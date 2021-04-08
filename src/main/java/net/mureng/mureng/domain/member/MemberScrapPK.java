package net.mureng.mureng.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class MemberScrapPK implements Serializable {
    private Long memberId;

    private Long expId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberScrapPK that = (MemberScrapPK) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(expId, that.expId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, expId);
    }

    @Builder
    public MemberScrapPK(Long memberId, Long expId) {
        this.memberId = memberId;
        this.expId = expId;
    }
}
