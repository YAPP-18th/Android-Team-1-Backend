package net.mureng.mureng.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.entity.MemberSetting;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="회원 모델", description="회원을 나타내는 모델")
public class MemberDto {
    @ApiModelProperty(value = "회원 기본키")
    private Long memberId;

    @ApiModelProperty(value = "회원 식별값")
    private String identifier;

    @ApiModelProperty(value = "이메일 주소")
    private String email;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "프로필 이미지 경로")
    private String image;

    @ApiModelProperty(value = "머렝 갯수")
    private int murengCount;

    @ApiModelProperty(value = "출석 횟수")
    private long attendanceCount;

    @ApiModelProperty(value = "마지막 출석 날짜 (yyyy-MM-dd)")
    private String lastAttendanceDate;

    @ApiModelProperty(value = "하루가 끝나는 시간 (hh:mm:ss)")
    private String dailyEndTime;

    @ApiModelProperty(value = "푸쉬 알림 여부")
    private boolean isPushActive;
}
