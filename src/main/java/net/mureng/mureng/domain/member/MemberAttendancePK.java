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
public class MemberAttendancePK implements Serializable {
    private Long memberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberAttendancePK that = (MemberAttendancePK) o;
        return Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    @Builder
    public MemberAttendancePK(Long memberId) {
        this.memberId = memberId;
    }
}
