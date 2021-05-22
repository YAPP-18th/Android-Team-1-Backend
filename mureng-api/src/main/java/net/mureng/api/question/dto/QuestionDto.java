package net.mureng.api.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.mureng.api.member.dto.MemberDto;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "질문 모델", description = "질문을 나타내는 모델")
public class QuestionDto {
    @ApiModelProperty(value = "카테고리", position = PropertyDisplayOrder.CATEGORY)
    @JsonProperty(index = PropertyDisplayOrder.CATEGORY)
    private String category;

    @NotEmpty
    @ApiModelProperty(value = "영문 내용", required = true, position = PropertyDisplayOrder.CONTENT)
    @JsonProperty(index = PropertyDisplayOrder.CONTENT)
    private String content;

    @ApiModelProperty(value = "한글 내용", position = PropertyDisplayOrder.KO_CONTENT)
    @JsonProperty(index = PropertyDisplayOrder.KO_CONTENT)
    private String koContent;

    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "질문 읽기 모델", description = "질문을 나타내는 읽기 모델")
    public static class ReadOnly extends QuestionDto {
        @ApiModelProperty(value = "질문 기본 키", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.QUESTION_ID)
        @JsonProperty(index = PropertyDisplayOrder.QUESTION_ID)
        private Long questionId;

        @ApiModelProperty(value = "연관된 단어 힌트들", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.WORD_HINTS)
        @JsonProperty(index = PropertyDisplayOrder.WORD_HINTS)
        private Set<WordHintDto.ReadOnly> wordHints;

        @ApiModelProperty(value = "작성자 회원 모델, null 인 경우 시스템에서 작성됨",
                accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.AUTHOR)
        @JsonProperty(index = PropertyDisplayOrder.AUTHOR)
        private MemberDto.ReadOnly author;

        @ApiModelProperty(value = "작성된 답변 수", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.REPLIES_COUNT)
        @JsonProperty(index = PropertyDisplayOrder.REPLIES_COUNT)
        private int repliesCount;
    }

    private static class PropertyDisplayOrder {
        private static final int QUESTION_ID    = 0;
        private static final int CATEGORY       = 1;
        private static final int CONTENT        = 2;
        private static final int KO_CONTENT     = 3;
        private static final int WORD_HINTS     = 4;
        private static final int AUTHOR         = 5;
        private static final int REPLIES_COUNT  = 6;
    }
}
