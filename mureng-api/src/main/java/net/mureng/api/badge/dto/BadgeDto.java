package net.mureng.api.badge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="뱃지 모델", description="뱃지 모델")
public class BadgeDto {
    @ApiModelProperty(value = "뱃지 이름")
    @JsonProperty(index = PropertyDisplayOrder.NAME)
    private String name;

    @ApiModelProperty(value = "뱃지 내용")
    @JsonProperty(index = PropertyDisplayOrder.CONTENT)
    private String content;

    private static class PropertyDisplayOrder {
        private static final int NAME                = 0;
        private static final int CONTENT             = 1;
    }
}
