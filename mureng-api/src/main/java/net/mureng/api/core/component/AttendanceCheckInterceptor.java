package net.mureng.api.core.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.api.core.dto.UserDetailsImpl;
import net.mureng.api.core.util.AuthenticationHelper;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.MemberRepository;
import net.mureng.core.member.service.MemberService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.persistence.EntityManager;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
@Slf4j
public class AttendanceCheckInterceptor implements HandlerInterceptor {
    private final MemberService memberService;
    private final EntityManager entityManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getUserPrincipal() == null) {
            return true;
        }

        Member member = memberService.findByIdentifier(request.getUserPrincipal().getName());
        if (isToday(member.getMemberAttendance().getLastAttendanceDate())) {
            return true;
        }

        member.increaseAttendanceCount();
        memberService.updateMember(member);
        updateSpringSecurityContext(member);
        log.info(request.getUserPrincipal().getName() + " attendance count increased");
        return true;
    }

    private boolean isToday(LocalDate localDate) {
        return Instant.now().atZone(ZoneId.of("Asia/Seoul")).toLocalDate().equals(localDate);
    }

    private void updateSpringSecurityContext(Member updatedMember) {
        // 기존 SecurityContext 상태와 똑같이 만들기 위해, 엔티티를 비영속 상태로 만든다.
        // TODO SecurityContext 속 엔티티를 영속 상태로 만들고, 그에 따라 코드 변경하기
        entityManager.detach(updatedMember);

        SecurityContextHolder.getContext().setAuthentication(
                AuthenticationHelper.getUserPassAuthentication(new UserDetailsImpl(updatedMember))
        );
    }
}
