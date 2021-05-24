package net.mureng.api.todayexpression.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="오늘의 표현 모델", description="오늘의 표현 모델")
public class TodayExpressionDto {

    @ApiModelProperty(value = "오늘의 표현", position = PropertyDisplayOrder.EXPRESSION)
    private String expression;

    @ApiModelProperty(value = "오늘의 표현 설명", position = PropertyDisplayOrder.MEANING)
    private String meaning;

    @ApiModelProperty(value = "오늘의 표현 예시", position = PropertyDisplayOrder.EXPRESSION_EXAMPLE)
    private String expressionExample;

    @ApiModelProperty(value = "오늘의 표현 예시 설명", position = PropertyDisplayOrder.EXPRESSION_EXAMPLE_MEANING)
    private String expressionExampleMeaning;

    @ApiModelProperty(value = "요청자의 스크랩 여부", position = PropertyDisplayOrder.SCRAPPED_BY_REQUESTER)
    private boolean scrappedByRequester;

    private static class PropertyDisplayOrder {
        private static final int EXPRESSION                 = 0;
        private static final int MEANING                    = 1;
        private static final int EXPRESSION_EXAMPLE         = 2;
        private static final int EXPRESSION_EXAMPLE_MEANING = 3;
        private static final int SCRAPPED_BY_REQUESTER      = 4;
    }
}
