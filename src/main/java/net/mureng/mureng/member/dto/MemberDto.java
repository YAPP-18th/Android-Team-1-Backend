package net.mureng.mureng.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.mureng.mureng.core.validation.annotation.DateFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="회원 모델", description="회원을 나타내는 모델")
public class MemberDto {
    @NotEmpty
    @ApiModelProperty(value = "회원 식별값", required = true)
    private String identifier;

    @Email
    @ApiModelProperty(value = "이메일 주소", required = true)
    private String email;

    @NotEmpty
    @ApiModelProperty(value = "닉네임", required = true)
    private String nickname;

    @ApiModelProperty(value = "프로필 이미지 경로")
    private String image;

    @SuperBuilder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value="회원 읽기 모델", description="읽기 전용 회원 모델")
    public static class ReadOnly extends MemberDto {
        @ApiModelProperty(value = "회원 고유 아이디", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private Long memberId;

        @ApiModelProperty(value = "머렝 개수", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private int murengCount;

        @ApiModelProperty(value = "출석 일수", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private int attendanceCount;

        @ApiModelProperty(value = "마지막 출석 날짜", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private String lastAttendanceDate;

        @ApiModelProperty(value = "푸쉬 알림 여부", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        private boolean isPushActive;
    }
}
