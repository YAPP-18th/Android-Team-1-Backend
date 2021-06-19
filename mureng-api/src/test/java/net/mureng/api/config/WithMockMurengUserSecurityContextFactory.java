package net.mureng.api.config;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.core.dto.UserDetailsImpl;
import net.mureng.core.member.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;

public class WithMockMurengUserSecurityContextFactory implements WithSecurityContextFactory<WithMockMurengUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockMurengUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetailsImpl principal =
                new UserDetailsImpl(Member.builder()
                        .memberId(1L)
                        .identifier("123")
                        .email("test@gmail.com")
                        .isActive(true)
                        .nickname("Test")
                        .image("tester-image-path")
                        .regDate(LocalDateTime.of(2020, 10, 14, 17, 11, 9))
                        .modDate(LocalDateTime.of(2020, 10, 14, 17, 11, 10))
                        .murengCount(0)
                        .build());
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}