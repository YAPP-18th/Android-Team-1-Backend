package net.mureng.api.reply.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="답변 좋아요 모델", description="답변 좋아요를 나타내는 모델")
public class ReplyLikesDto {
    @ApiModelProperty(value = "사용자 id", position = PropertyDisplayOrder.MEMBER_ID)
    private Long memberId;

    @ApiModelProperty(value = "답변 id", position = PropertyDisplayOrder.REPLY_ID)
    private Long replyId;

    @Builder.Default
    @ApiModelProperty(value = "좋아요", position = PropertyDisplayOrder.LIKES)
    private boolean likes = true;

    private static class PropertyDisplayOrder {
        private static final int MEMBER_ID    = 0;
        private static final int REPLY_ID     = 1;
        private static final int LIKES        = 2;
    }
}
