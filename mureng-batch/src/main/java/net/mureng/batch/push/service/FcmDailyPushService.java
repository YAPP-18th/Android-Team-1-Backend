package net.mureng.batch.push.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.service.TodayQuestionService;
import net.mureng.push.dto.NotificationRequest;
import net.mureng.push.service.FcmService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmDailyPushService {
    private static final String DAILY_PUSH_TITLE = "오늘의 영어 질문";
    private static final String DAILY_PUSH_CLICK_ACTION = "MAIN";
    private static final String DAILY_PUSH_CHANNEL_ID = "DAILY_CHANNEL";

    private final FcmService fcmService;
    private final TodayQuestionService todayQuestionService;

    public void pushToMember(Member member) {
        fcmService.send(NotificationRequest.builder()
                .token(member.getFcmToken())
                .title(DAILY_PUSH_TITLE)
                .message(todayQuestionService.getTodayQuestionByMemberId(member.getMemberId()).getContent())
                .clickAction(DAILY_PUSH_CLICK_ACTION)
                .channelId(DAILY_PUSH_CHANNEL_ID)
                .build());
    }
}
