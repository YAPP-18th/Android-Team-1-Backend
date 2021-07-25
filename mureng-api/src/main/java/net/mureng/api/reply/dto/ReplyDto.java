package net.mureng.api.reply.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.mureng.api.member.dto.MemberDto;
import net.mureng.api.question.dto.QuestionDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="답변 모델", description="질문에 대한 답변을 나타내는 모델")
public class ReplyDto {
    @Min(1)
    @ApiModelProperty(value = "답변하려는 질문의 기본키", required = true, position = PropertyDisplayOrder.QUESTION_ID)
    @JsonProperty(index = PropertyDisplayOrder.QUESTION_ID)
    private Long questionId;

    @NotEmpty
    @ApiModelProperty(value = "답변 내용", required = true, position = PropertyDisplayOrder.CONTENT)
    @JsonProperty(index = PropertyDisplayOrder.CONTENT)
    private String content;

    @ApiModelProperty(value = "이미지 경로", required = true, position = PropertyDisplayOrder.IMAGE)
    @JsonProperty(index = PropertyDisplayOrder.IMAGE)
    private String image;

    @SuperBuilder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value="답변 읽기 모델", description="질문에 대한 답변을 나타내는 읽기 전용 모델")
    public static class ReadOnly extends ReplyDto {
        @ApiModelProperty(value = "답변 기본키", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        @JsonProperty(index = PropertyDisplayOrder.REPLY_ID)
        private Long replyId;

        @ApiModelProperty(value = "작성 날짜", position = PropertyDisplayOrder.REG_DATE)
        @JsonProperty(index = PropertyDisplayOrder.REG_DATE)
        private LocalDate regDate;

        @ApiModelProperty(value = "좋아요 갯수", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.REPLY_LIKE_COUNT)
        @JsonProperty(index = PropertyDisplayOrder.REPLY_LIKE_COUNT)
        private int replyLikeCount;

        @ApiModelProperty(value = "질문 모델", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.QUESTION)
        @JsonProperty(index = PropertyDisplayOrder.QUESTION)
        private QuestionDto.ReadOnly question;

        @ApiModelProperty(value = "작성자 회원 모델", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.AUTHOR)
        @JsonProperty(index = PropertyDisplayOrder.AUTHOR)
        private MemberDto.ReadOnly author;

        @ApiModelProperty(value = "이 답변이 요청자 본인의 것인지 여부, null 인 경우 확인 불가",
                accessMode = ApiModelProperty.AccessMode.READ_ONLY, position = PropertyDisplayOrder.REQUESTED_BY_AUTHOR)
        @JsonProperty(index = PropertyDisplayOrder.REQUESTED_BY_AUTHOR)
        private Boolean requestedByAuthor;

        @ApiModelProperty(value = "해당 답변에 대해 요청자가 좋아요를 눌렀는 지",
                accessMode = ApiModelProperty.AccessMode.READ_ONLY, position = PropertyDisplayOrder.LIKED_BY_MEMBER)
        @JsonProperty(index = PropertyDisplayOrder.LIKED_BY_MEMBER)
        private Boolean likedByRequester;
    }

    private static class PropertyDisplayOrder {
        private static final int REPLY_ID               = 0;
        private static final int QUESTION_ID            = 1;
        private static final int CONTENT                = 2;
        private static final int IMAGE                  = 3;
        private static final int REG_DATE               = 4;
        private static final int REPLY_LIKE_COUNT       = 5;
        private static final int QUESTION               = 6;
        private static final int AUTHOR                 = 7;
        private static final int REQUESTED_BY_AUTHOR    = 8;
        private static final int LIKED_BY_MEMBER        = 9;
    }
}

