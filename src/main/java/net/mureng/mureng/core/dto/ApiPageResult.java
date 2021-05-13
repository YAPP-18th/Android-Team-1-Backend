package net.mureng.mureng.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@ApiModel(value="페이징이 적용된 API 응답 모델")
public class ApiPageResult extends ApiResult<List<?>> {

    @ApiModelProperty(value = "페이지 번호")
    private final int currentPage;

    @ApiModelProperty(value = "총합 페이지")
    private final int totalPage;

    @ApiModelProperty(value = "페이지 크기")
    private final int pageSize;

    public ApiPageResult(Page<?> data, String message, ApiPageRequest pageRequest) {
        super(message, data.getContent());
        this.currentPage = pageRequest.getPage();
        this.pageSize = pageRequest.getSize();
        this.totalPage = data.getTotalPages();
    }

    public static ApiPageResult ok(Page<?> data, ApiPageRequest pageRequest) {
        return ok(data, "ok", pageRequest);
    }

    public static ApiPageResult ok(Page<?> data, String message, ApiPageRequest pageRequest) {
        return new ApiPageResult(data, message, pageRequest);
    }
}
