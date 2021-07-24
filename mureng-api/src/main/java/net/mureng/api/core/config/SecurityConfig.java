package net.mureng.api.core.config;

import lombok.RequiredArgsConstructor;
import net.mureng.api.core.component.AttendanceCheckInterceptor;
import net.mureng.api.core.jwt.component.JwtAuthenticationEntryPoint;
import net.mureng.api.core.jwt.component.JwtAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String[] EXCLUDED_URLS = {
            "/api/member/signup",
            "/api/member/nickname-exists/**",
            "/api/member/user-exists/**",
            "/api/test",
            "/api/test-failure",
            "/api/jwt",
            "/api/member/signin",
            "/api/member/refresh",
            "/",
            "/api/fcm-token"
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AttendanceCheckInterceptor attendanceCheckInterceptor;

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers(EXCLUDED_URLS).permitAll()
                        .anyRequest().authenticated()
                .and()
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
                                "/swagger-resources/**", "/configuration/security",
                                "/swagger-ui.html", "/webjars/**","/swagger/**"); 
    }
}
