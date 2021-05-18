package net.mureng.mureng.question.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="질문 모델", description="질문을 나타내는 모델")
public class QuestionDto {
    @ApiModelProperty(value = "카테고리")
    private String category;

    @NotEmpty
    @ApiModelProperty(value = "영문 내용", required = true)
    private String content;

    @ApiModelProperty(value = "한글 내용")
    private String koContent;

    @SuperBuilder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value="질문 읽기 모델", description="질문을 나타내는 읽기 모델")
    public static class ReadOnly extends QuestionDto {
        @ApiModelProperty(value = "질문 기본 키", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private Long questionId;

        @ApiModelProperty(value = "연관된 단어 힌트들", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private Set<WordHintDto.ReadOnly> wordHints;

        @ApiModelProperty(value = "작성된 답변 수", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private int repliesCount;
    }
}
