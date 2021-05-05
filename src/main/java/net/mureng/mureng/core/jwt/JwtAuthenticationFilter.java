package net.mureng.mureng.core.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {
    private JwtResolver jwtResolver;
    private JwtValidator jwtValidator;

    public JwtAuthenticationFilter(JwtResolver jwtResolver, JwtValidator jwtValidator) {
        this.jwtResolver = jwtResolver;
        this.jwtValidator = jwtValidator;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증하는 filter를 FilterChain에 등록
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtResolver.resolveToken((HttpServletRequest) servletRequest);

        if (jwtValidator.validateToken(token)) { // token 검증
            Authentication auth = jwtResolver.getAuthentication(token); // 인증 객체 생성
            SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContextHolder에 인증 객체 저장
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
