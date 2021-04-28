package net.mureng.mureng.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@NoArgsConstructor
@Entity
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false, length = 8)
    private String nickname;

    private String image;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate = LocalDateTime.now();

    @Column(name = "mureng_count", nullable = false)
    private Long murengCount = 0L;

    @OneToOne(mappedBy = "member")
    private MemberAttendance memberAttendance;

    @OneToOne(mappedBy = "member")
    private MemberSetting memberSetting;

    @Builder
    public Member(Long memberId, String identifier, String email, Boolean isActive, String nickname, String image, LocalDateTime regDate, LocalDateTime modDate, Long murengCount) {
        this.memberId = memberId;
        this.identifier = identifier;
        this.email = email;
        this.isActive = isActive;
        this.nickname = nickname;
        this.image = image;
        this.regDate = regDate;
        this.modDate = modDate;
        this.murengCount = murengCount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
