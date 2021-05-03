package net.mureng.mureng.member.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false)
    private String email;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private String nickname;

    private String image;

    @Builder.Default
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate = LocalDateTime.now();

    @Column(name = "mureng_count", nullable = false)
    private Long murengCount = 0L;

    @OneToOne(mappedBy = "member")
    @Builder.Default
    private MemberAttendance memberAttendance = new MemberAttendance();

    @OneToOne(mappedBy = "member")
    @Builder.Default
    private MemberSetting memberSetting = new MemberSetting();
}
