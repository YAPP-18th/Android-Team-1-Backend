package net.mureng.push.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NotificationRequest {
    private String token;
    private String title;
    private String message;
    private String image;
    private String clickAction;
    private String channelId;
}
