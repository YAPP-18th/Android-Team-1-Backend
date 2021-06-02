package net.mureng.api.core.oauth2.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="OAuth Profile", description="OAuth Profile 모델")
public class OAuth2Profile {
    @ApiModelProperty(value = "사용자 id")
    private String identifier;
}
