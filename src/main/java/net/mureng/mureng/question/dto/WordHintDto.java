package net.mureng.mureng.question.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.mureng.mureng.question.entity.Question;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="단어 힌트 모델", description="단어 힌트를 나타내는 모델")
public class WordHintDto {
    @ApiModelProperty(value = "단어", position = 1)
    private String word;

    @ApiModelProperty(value = "뜻", position = 2)
    private String meaning;

    @SuperBuilder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value="단어 힌트 읽기 모델", description="단어 힌트를 나타내는 읽기 모델")
    public static class ReadOnly extends WordHintDto {
        @ApiModelProperty(value = "단어 힌트 기본키", accessMode = ApiModelProperty.AccessMode.READ_ONLY, position = 0)
        private Long hintId;
    }
}
