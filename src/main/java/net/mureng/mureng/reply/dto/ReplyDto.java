package net.mureng.mureng.reply.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.question.dto.QuestionDto;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="답변 모델", description="질문에 대한 답변을 나타내는 모델")
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

    @ApiModelProperty(value = "좋아요 갯수")
    private int replyLikeCount;

    @ApiModelProperty(hidden = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private QuestionDto question;

    @ApiModelProperty(hidden = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private MemberDto author;
}
