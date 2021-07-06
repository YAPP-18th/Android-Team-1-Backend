package net.mureng.push.service;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import net.mureng.push.dto.NotificationRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class FcmService {
    public void send(final NotificationRequest notificationRequest) {
        Message message = Message.builder()
                .setToken(notificationRequest.getToken())
                .setNotification(new Notification(notificationRequest.getTitle(),
                        notificationRequest.getMessage()))
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(notificationRequest.getTitle(),
                                notificationRequest.getMessage(),
                                notificationRequest.getImage()))
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder()
                                .setClickAction(notificationRequest.getClickAction())
                                .setChannelId(notificationRequest.getChannelId())
                                .build())
                        .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            log.info("Sent message: " + response);
        } catch (Exception e) {
            log.error("Sent message was failed with token : " + notificationRequest.getToken(), e);
        }
    }
}
