package net.mureng.core.cookie.entity;

import lombok.*;
import net.mureng.core.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cookie_acquirement")
public class CookieAcquirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate = LocalDate.now();
}
