package net.mureng.api.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="단어 힌트 모델", description="단어 힌트를 나타내는 모델")
public class WordHintDto {
    @ApiModelProperty(value = "단어", position = PropertyDisplayOrder.WORD)
    @JsonProperty(index = PropertyDisplayOrder.WORD)
    private String word;

    @ApiModelProperty(value = "뜻", position = PropertyDisplayOrder.MEANING)
    @JsonProperty(index = PropertyDisplayOrder.MEANING)
    private String meaning;

    @SuperBuilder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value="단어 힌트 읽기 모델", description="단어 힌트를 나타내는 읽기 모델")
    public static class ReadOnly extends WordHintDto {
        @ApiModelProperty(value = "단어 힌트 기본키", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.HINT_ID)
        @JsonProperty(index = PropertyDisplayOrder.HINT_ID)
        private Long hintId;
    }

    private static class PropertyDisplayOrder {
        private static final int HINT_ID        = 0;
        private static final int WORD           = 1;
        private static final int MEANING        = 2;
    }
}
