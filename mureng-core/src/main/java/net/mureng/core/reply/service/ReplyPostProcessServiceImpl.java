package net.mureng.core.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.service.MemberService;
import net.mureng.core.reply.entity.Reply;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyPostProcessServiceImpl implements ReplyPostProcessService {

    @Transactional
    public void postProcess(Reply reply) {
        reply.getAuthor().increaseMurengCount();
    }
}
