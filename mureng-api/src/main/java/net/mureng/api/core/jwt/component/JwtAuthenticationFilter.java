package net.mureng.api.core.jwt.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.core.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtResolver.resolveToken(servletRequest);

        if (jwtValidator.validateToken(token)) {
            try {
                setAuthToSecurityContextHolder(token);
            } catch (ResourceNotFoundException e) {
                writeNotFoundResponse(servletResponse, e);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setAuthToSecurityContextHolder(String token) {
        Authentication auth = jwtResolver.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void writeNotFoundResponse(HttpServletResponse response, ResourceNotFoundException ex) {
        final String msg = "토큰에 해당하는 사용자가 없습니다.";
        log.error(msg);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json");
        ApiResult<?> errorResponse = ApiResult.fail(msg);
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
        }catch (IOException e){
            log.error("Json failed : ", e);
        }
    }
}
