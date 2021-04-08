package net.mureng.mureng.domain.badge;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "BADGE_ACCOMPLISHED")
public class BadgeAccomplished {

    @EmbeddedId
    private BadgeAccomplishedPK id;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Builder
    public BadgeAccomplished(BadgeAccomplishedPK id, LocalDateTime regDate) {
        this.id = id;
        this.regDate = regDate;
    }
}

