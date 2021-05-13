package net.mureng.mureng.question.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.WordHint;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="질문 모델", description="질문을 나타내는 모델")
public class QuestionDto {
    @ApiModelProperty(value = "질문 기본키")
    private Long questionId;

    @ApiModelProperty(value = "카테고리")
    private String category;

    @ApiModelProperty(value = "영문 내용")
    private String content;

    @ApiModelProperty(value = "한글 내용")
    private String koContent;

    @ApiModelProperty(value = "연관된 단어 힌트들")
    private Set<WordHintDto> wordHints;
}
