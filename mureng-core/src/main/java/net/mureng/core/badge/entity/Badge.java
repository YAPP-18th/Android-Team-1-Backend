package net.mureng.core.badge.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "badge")
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
