package net.mureng.mureng.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter @Setter
@NoArgsConstructor
@ApiModel(value="페이징 API 요청 모델")
public class ApiPageRequest {
    @ApiModelProperty(value = "요청 페이지 번호 (0부터 시작)", example = "0")
    private int page = 0;

    @ApiModelProperty(value = "요청 페이지 크기", example = "10")
    private int size = 10;

    public Pageable convert() {
        return PageRequest.of(page, size);
    }
}
