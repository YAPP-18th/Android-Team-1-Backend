package net.mureng.push.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.member.entity.FcmToken;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.FcmTokenRepository;
import net.mureng.core.reply.entity.Reply;
import net.mureng.push.dto.NotificationRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FcmLikePushServiceImpl implements FcmLikePushService {
    private static final String LIKE_PUSH_TITLE = "머렝";
    private static final String LIKE_PUSH_MESSAGE_TEMPLATE = "%s님이 회원님의 글을 좋아해요❤";
    private static final String LIKE_PUSH_CLICK_ACTION = "MAIN";
    private static final String LIKE_PUSH_CHANNEL_ID = "LIKE_CHANNEL";

    private final FcmTokenRepository fcmTokenRepository;
    private final FcmService fcmService;

    @Override
    public void pushToAuthor(Reply reply, Member likedMember) {
        Optional<FcmToken> fcmToken = fcmTokenRepository.findByMemberMemberId(reply.getAuthor().getMemberId());
        if (! reply.getAuthor().getMemberSetting().isLikePushActive() || fcmToken.isEmpty()) {
            return;
        }

        fcmService.send(NotificationRequest.builder()
                .token(fcmToken.get().getToken())
                .title(LIKE_PUSH_TITLE)
                .message(String.format(LIKE_PUSH_MESSAGE_TEMPLATE, likedMember.getNickname()))
                .clickAction(LIKE_PUSH_CLICK_ACTION)
                .channelId(LIKE_PUSH_CHANNEL_ID)
                .build());
    }
}
