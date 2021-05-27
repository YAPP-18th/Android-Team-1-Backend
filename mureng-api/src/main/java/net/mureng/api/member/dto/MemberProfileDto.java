package net.mureng.api.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="회원 프로필 모델", description="회원 프로필을 나타내는 모델")
public class MemberProfileDto {
    @ApiModelProperty(value = "사용자 정보")
    @JsonProperty(index = PropertyDisplayOrder.MEMBER)
    private MemberDto member;

    @ApiModelProperty(value = "스크랩 목록")
    @JsonProperty(index = PropertyDisplayOrder.SCRAPLIST)
    private List<TodayExpressionDto> scrapList;

    @ApiModelProperty(value = "요청자의 프로필인지")
    @JsonProperty(index = PropertyDisplayOrder.REQUESTER_PROFILE)
    private boolean requesterProfile;

    private static class PropertyDisplayOrder {
        private static final int MEMBER                = 0;
        private static final int SCRAPLIST             = 1;
        private static final int REQUESTER_PROFILE     = 2;
    }
}
