package net.mureng.mureng.domain.badge;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="badge_id")
    private Long badgeId;

    private String name;

    private String content;
}
