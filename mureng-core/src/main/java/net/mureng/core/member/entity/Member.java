package net.mureng.core.member.entity;

import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @NotNull
    @Column(nullable = false, unique = true)
    private String identifier;

    @Column(unique = true)
    private String email;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @NotNull
    @Column(nullable = false, unique = true)
    private String nickname;

    private String image;

    private String fcmToken;

    @Builder.Default
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "mureng_count", nullable = false)
    private int murengCount = 0;

    @Embedded
    @Builder.Default
    private MemberAttendance memberAttendance = new MemberAttendance();

    @Embedded
    @Builder.Default
    private MemberSetting memberSetting = new MemberSetting();

    public boolean isRequesterProfile(Long memberId){
        return this.memberId == memberId;
    }

    public void increaseMurengCount() {
        this.murengCount++;
        this.modDate = LocalDateTime.now();
    }

    public void updateMember(Member member) {
        if (StringUtils.hasText(member.getNickname())) {
            this.nickname = member.getNickname();
            this.modDate = LocalDateTime.now();
        }

        if (StringUtils.hasText(member.getImage())) {
            this.image = member.getImage();
            this.modDate = LocalDateTime.now();
        }
    }
}
