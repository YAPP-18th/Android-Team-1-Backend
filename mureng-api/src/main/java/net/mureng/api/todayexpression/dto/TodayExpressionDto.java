package net.mureng.api.todayexpression.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="오늘의 표현 모델", description="오늘의 표현 모델")
public class TodayExpressionDto {
    @NotEmpty
    @ApiModelProperty(value = "오늘의 표현 ID", required = true)
    @JsonProperty(index = PropertyDisplayOrder.EXP_ID)
    private Long expId;

    @NotEmpty
    @ApiModelProperty(value = "오늘의 표현", position = PropertyDisplayOrder.EXPRESSION)
    private String expression;

    @NotEmpty
    @ApiModelProperty(value = "오늘의 표현 설명", position = PropertyDisplayOrder.MEANING)
    private String meaning;

    @NotEmpty
    @ApiModelProperty(value = "오늘의 표현 예시", position = PropertyDisplayOrder.EXPRESSION_EXAMPLE)
    private String expressionExample;

    @NotEmpty
    @ApiModelProperty(value = "오늘의 표현 예시 설명", position = PropertyDisplayOrder.EXPRESSION_EXAMPLE_MEANING)
    private String expressionExampleMeaning;

    @NotEmpty
    @ApiModelProperty(value = "요청자의 스크랩 여부", position = PropertyDisplayOrder.SCRAPPED_BY_REQUESTER)
    private boolean scrappedByRequester;

    private static class PropertyDisplayOrder {
        private static final int EXP_ID                     = 0;
        private static final int EXPRESSION                 = 1;
        private static final int MEANING                    = 2;
        private static final int EXPRESSION_EXAMPLE         = 3;
        private static final int EXPRESSION_EXAMPLE_MEANING = 4;
        private static final int SCRAPPED_BY_REQUESTER      = 5;
    }
}
