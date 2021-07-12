package net.mureng.core.reply.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.core.cookie.repository.CookieAcquirementRepository;
import net.mureng.core.cookie.service.CookieAcquirementService;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.MemberRepository;
import net.mureng.core.reply.entity.Reply;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static net.mureng.core.core.message.ErrorMessage.ALREADY_ANSWERED_MEMBER;
import static net.mureng.core.core.message.ErrorMessage.ALREADY_COOKIE_ACQUIRED;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyPostProcessServiceImpl implements ReplyPostProcessService {
    private final MemberRepository memberRepository;
    private final CookieAcquirementService cookieAcquirementService;
    private final CookieAcquirementRepository cookieAcquirementRepository;

    @Transactional
    public void postProcess(Reply reply) {
        Member member = reply.getAuthor();
        if( !cookieAcquirementService.isAlreadyCookieAcquiredToday(member.getMemberId()) ) {
            log.info(ALREADY_COOKIE_ACQUIRED);
            cookieAcquirementService.acquireMurengCookie(member);
        }

        memberRepository.saveAndFlush(member);
    }
}
