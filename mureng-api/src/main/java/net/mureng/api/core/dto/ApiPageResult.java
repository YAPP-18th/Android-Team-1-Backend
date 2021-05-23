package net.mureng.api.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@ApiModel(value="페이징 API 응답 모델")
public class ApiPageResult<T> extends ApiResult<List<T>> {

    @ApiModelProperty(value = "페이지 번호", position = 2)
    private final int currentPage;

    @ApiModelProperty(value = "총합 페이지", position = 3)
    private final int totalPage;

    @ApiModelProperty(value = "페이지 크기", position = 4)
    private final int pageSize;

    @ApiModelProperty(value = "전체 답변 개수",  position = 5)
    private final long totalNum;

    public ApiPageResult(Page<T> data, String message) {
        super(message, data.getContent());
        this.currentPage = data.getPageable().getPageNumber();
        this.pageSize = data.getPageable().getPageSize();
        this.totalPage = data.getTotalPages();
        this.totalNum = data.getTotalElements();
    }

    public static <T> ApiPageResult<T> ok(Page<T> data) {
        return ok(data, "ok");
    }

    public static <T> ApiPageResult<T> ok(Page<T> data, String message) {
        return new ApiPageResult<>(data, message);
    }
}
