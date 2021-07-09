package net.mureng.core.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.cookie.service.CookieAchievementService;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.MemberRepository;
import net.mureng.core.reply.entity.Reply;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyPostProcessServiceImpl implements ReplyPostProcessService {
    private final MemberRepository memberRepository;
    private final CookieAchievementService cookieAchievementService;

    @Transactional
    public void postProcess(Reply reply) {
        Member member = reply.getAuthor();
        cookieAchievementService.achievementMurengCookie(member);

        memberRepository.saveAndFlush(member);
    }
}
