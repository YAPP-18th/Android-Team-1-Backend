package net.mureng.api.core.config;

import lombok.RequiredArgsConstructor;
import net.mureng.api.core.jwt.component.JwtAuthenticationFilter;
import net.mureng.api.core.jwt.component.JwtResolver;
import net.mureng.api.core.jwt.component.JwtValidator;
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

    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers("/api/member/signup",
                                "/api/member/nickname-exists/**",
                                "/api/member/user-exists/**").anonymous()
                        .antMatchers("/api/test", "/api/test-failure").anonymous()
                        .antMatchers("/api/jwt", "/api/member/signin").anonymous()
                        .antMatchers("/").anonymous()
                        .anyRequest().authenticated()
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtResolver, jwtValidator), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
                                "/swagger-resources/**", "/configuration/security",
                                "/swagger-ui.html", "/webjars/**","/swagger/**"); 
    }
}
