package net.mureng.mureng.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.mureng.mureng.core.validation.annotation.DateFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="회원 모델", description="회원을 나타내는 모델")
public class MemberDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(value = "회원 기본키")
    private Long memberId;

    @NotEmpty
    @ApiModelProperty(value = "회원 식별값")
    private String identifier;

    @Email
    @ApiModelProperty(value = "이메일 주소")
    private String email;

    @NotEmpty
    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "프로필 이미지 경로")
    private String image;

    @ApiModelProperty(value = "머렝 갯수")
    private int murengCount;

    @ApiModelProperty(value = "출석 횟수")
    private int attendanceCount;

    @DateFormat
    @ApiModelProperty(value = "마지막 출석 날짜", allowableValues = "yyyy-MM-dd")
    private String lastAttendanceDate;

    @ApiModelProperty(value = "푸쉬 알림 여부")
    private boolean isPushActive;
}
