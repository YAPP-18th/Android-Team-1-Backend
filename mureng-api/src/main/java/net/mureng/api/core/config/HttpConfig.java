package net.mureng.api.core.config;

import lombok.RequiredArgsConstructor;
import net.mureng.api.core.component.AttendanceCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class HttpConfig implements WebMvcConfigurer {
    private final AttendanceCheckInterceptor attendanceCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(attendanceCheckInterceptor)
                .excludePathPatterns(SecurityConfig.EXCLUDED_URLS);
    }
}
