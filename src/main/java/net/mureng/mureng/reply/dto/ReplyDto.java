package net.mureng.mureng.reply.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
    @ApiModelProperty(value = "답변 기본키")
    private Long replyId;

    @ApiModelProperty(value = "질문 기본키")
    private Long questionId;

    @NotEmpty
    @ApiModelProperty(value = "답변 내용")
    private String content;

    @ApiModelProperty(value = "이미지")
    private String image;
}
