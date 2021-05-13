package net.mureng.mureng.reply.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="답변 모델", description="질문에 대한 답변을 나타내는 모델")
public class ReplyDto {
    @ApiModelProperty(value = "답변 기본키")
    private Long replyId;

    @JsonIgnore
    @ApiModelProperty(value = "질문 기본키")
    private Long questionId;

    @NotEmpty
    @ApiModelProperty(value = "답변 내용")
    private String content;

    @ApiModelProperty(value = "이미지")
    private String image;
}
