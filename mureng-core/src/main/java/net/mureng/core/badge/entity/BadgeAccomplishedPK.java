package net.mureng.core.badge.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class BadgeAccomplishedPK implements Serializable {
    private Long badgeId;
    private Long memberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadgeAccomplishedPK that = (BadgeAccomplishedPK) o;
        return Objects.equals(badgeId, that.badgeId) && Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(badgeId, memberId);
    }

    @Builder
    public BadgeAccomplishedPK(Long badgeId, Long memberId) {
        this.badgeId = badgeId;
        this.memberId = memberId;
    }
}
