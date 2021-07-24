package net.mureng.api.config;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.core.dto.UserDetailsImpl;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.entity.MemberAttendance;
import net.mureng.core.member.entity.MemberSetting;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class WithMockMurengUserSecurityContextFactory implements WithSecurityContextFactory<WithMockMurengUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockMurengUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetailsImpl principal =
                new UserDetailsImpl(Member.builder()
                        .memberId(1L)
                        .identifier("identity")
                        .email("test@gmail.com")
                        .isActive(true)
                        .nickname("테스트유저")
                        .image("/reply/1621586761110.png")
                        .memberAttendance(MemberAttendance.builder()
                                .attendanceCount(0)
                                .lastAttendanceDate(LocalDate.of(2021, 5, 13))
                                .build())
                        .memberSetting(MemberSetting.builder()
                                .isDailyPushActive(true)
                                .isLikePushActive(true)
                                .build())
                        .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                        .modDate(LocalDateTime.of(2020, 10, 14, 17, 11, 10))
                        .build());
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}