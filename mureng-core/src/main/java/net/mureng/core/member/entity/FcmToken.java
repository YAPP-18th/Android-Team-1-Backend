package net.mureng.core.member.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fcm_token")
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long idx;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Column(nullable = false, unique = true)
    private String token;
}
