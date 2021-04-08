package net.mureng.mureng.domain.badge;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="badge_id", nullable = false)
    private Long badgeId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private String content;

    @Builder
    public Badge(Long badgeId, String name, String content) {
        this.badgeId = badgeId;
        this.name = name;
        this.content = content;
    }
}
