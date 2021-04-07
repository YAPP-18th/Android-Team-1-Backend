package net.mureng.mureng.domain.badge;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

}
