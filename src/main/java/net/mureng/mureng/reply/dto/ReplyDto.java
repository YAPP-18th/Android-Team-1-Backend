package net.mureng.mureng.reply.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.question.dto.QuestionDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="답변 모델", description="질문에 대한 답변을 나타내는 모델")
public class ReplyDto {
    @Min(1)
    @ApiModelProperty(value = "답변하려는 질문의 기본키", required = true)
    private Long questionId;

    @NotEmpty
    @ApiModelProperty(value = "답변 내용", required = true)
    private String content;

    @ApiModelProperty(value = "이미지 경로", required = true)
    private String image;

    @SuperBuilder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value="답변 읽기 모델", description="질문에 대한 답변을 나타내는 읽기 전용 모델")
    public static class ReadOnly extends ReplyDto {
        @ApiModelProperty(value = "답변 기본키", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private Long replyId;

        @ApiModelProperty(value = "좋아요 갯수", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private int replyLikeCount;

        @ApiModelProperty(value = "질문 모델", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private QuestionDto.ReadOnly question;

        @ApiModelProperty(value = "작성자 회원 모델", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private MemberDto.ReadOnly author;
    }
}
