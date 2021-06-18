package net.mureng.push.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.member.entity.Member;
import net.mureng.core.reply.entity.Reply;
import net.mureng.push.dto.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmLikePushServiceImpl implements FcmLikePushService {
    private static final String LIKE_PUSH_TITLE = "오늘의 영어 질문";
    private static final String LIKE_PUSH_MESSAGE_TEMPLATE = "%s님이 회원님의 글을 좋아해요❤";
    private static final String LIKE_PUSH_CLICK_ACTION = "MAIN";
    private static final String LIKE_PUSH_CHANNEL_ID = "LIKE_CHANNEL";

    private final FcmService fcmService;

    @Override
    public void pushToAuthor(Reply reply, Member likedMember) {
        fcmService.send(NotificationRequest.builder()
                .token(reply.getAuthor().getFcmToken())
                .title(LIKE_PUSH_TITLE)
                .message(String.format(LIKE_PUSH_MESSAGE_TEMPLATE, likedMember.getNickname()))
                .clickAction(LIKE_PUSH_CLICK_ACTION)
                .channelId(LIKE_PUSH_CHANNEL_ID)
                .build());
    }
}
