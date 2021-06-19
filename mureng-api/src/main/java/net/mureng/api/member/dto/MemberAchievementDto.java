package net.mureng.api.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.mureng.api.badge.dto.BadgeDto;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="사용자 성과 모델", description="사용자 프로필 성과 모델")
public class MemberAchievementDto {
    @ApiModelProperty(value = "사용자 정보")
    @JsonProperty(index = PropertyDisplayOrder.MEMBER)
    private MemberDto member;

    @ApiModelProperty(value = "사용자 뱃지 목록")
    @JsonProperty(index = PropertyDisplayOrder.BADGES)
    private List<BadgeDto> badges;

    @ApiModelProperty(value = "요청자의 프로필인지")
    @JsonProperty(index = PropertyDisplayOrder.REQUESTER_PROFILE)
    private boolean requesterProfile;

    private static class PropertyDisplayOrder {
        private static final int MEMBER                = 0;
        private static final int BADGES                = 1;
        private static final int REQUESTER_PROFILE     = 2;
    }
}
