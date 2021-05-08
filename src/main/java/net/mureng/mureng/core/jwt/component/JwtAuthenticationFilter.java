package net.mureng.mureng.core.jwt.component;

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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtResolver.resolveToken((HttpServletRequest) servletRequest);

        if (jwtValidator.validateToken(token)) {
            Authentication auth = jwtResolver.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
