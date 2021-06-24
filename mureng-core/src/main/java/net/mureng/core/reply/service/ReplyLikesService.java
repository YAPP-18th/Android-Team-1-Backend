package net.mureng.core.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.service.BadgeAccomplishedService;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.member.entity.Member;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.entity.ReplyLikes;
import net.mureng.core.reply.entity.ReplyLikesPK;
import net.mureng.core.reply.repository.ReplyLikesRepository;
import net.mureng.push.service.FcmLikePushService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyLikesService {
    private final ReplyLikesRepository replyLikesRepository;
    private final ReplyService replyService;
    private final BadgeAccomplishedService badgeAccomplishedService;
    private final FcmLikePushService fcmLikePushService;

    @Transactional
    public ReplyLikes postReplyLikes(Member member, Long replyId){
        Reply reply = replyService.findById(replyId);

        ReplyLikesPK replyLikesPK = new ReplyLikesPK(member.getMemberId(), reply.getReplyId());

        if(replyLikesRepository.existsById(replyLikesPK))
            throw new BadRequestException("이미 좋아요를 눌렀습니다.");

        ReplyLikes replyLikes = ReplyLikes.builder().id(replyLikesPK).member(member).reply(reply).build();

        replyLikesRepository.saveAndFlush(replyLikes);

        badgeAccomplishedService.createCelebrityMureng(replyId);
        fcmLikePushService.pushToAuthor(reply, member);

        return replyLikes;
    }

    @Transactional
    public void deleteReplyLikes(Member member, Long replyId){
        Reply reply = replyService.findById(replyId);

        ReplyLikesPK replyLikesPK = new ReplyLikesPK(member.getMemberId(), reply.getReplyId());

        if(! replyLikesRepository.existsById(replyLikesPK))
            throw new BadRequestException("이미 좋아요를 취소했습니다.");

        replyLikesRepository.deleteById(replyLikesPK);
    }

}
