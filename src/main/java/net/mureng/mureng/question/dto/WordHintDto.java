package net.mureng.mureng.question.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.mureng.mureng.question.entity.Question;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordHintDto {
    @ApiModelProperty(value = "단어 힌트 기본키")
    private Long hintId;

    @ApiModelProperty(value = "단어")
    private String word;

    @ApiModelProperty(value = "뜻")
    private String meaning;
}
