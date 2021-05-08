package net.mureng.mureng.core.oauth2.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="OAuth Profile", description="OAuth Profile 모델")
public class OAuth2Profile {
    @Email
    @ApiModelProperty(value = "이메일 주소")
    private String email;
}
