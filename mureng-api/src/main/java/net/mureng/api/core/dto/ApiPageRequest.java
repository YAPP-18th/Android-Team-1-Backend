package net.mureng.api.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.mureng.core.core.exception.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="페이징 API 요청 모델")
@EqualsAndHashCode
public class ApiPageRequest {
    @ApiModelProperty(value = "요청 페이지 번호 (0부터 시작)", example = "0")
    private int page = 0;

    @ApiModelProperty(value = "요청 페이지 크기 (기본 : 10)", example = "10")
    private int size = 10;

    @ApiModelProperty(value = "페이지 정렬 방식 (기본 : popular)", allowableValues = "popular, newest")
    private PageSort sort = PageSort.POPULAR;

    public void setSort(String sortString) {
        this.sort = PageSort.create(sortString);
    }

    public Pageable convert() {
        return PageRequest.of(page, size);
    }

    public Pageable convertWithNewestSort() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate"));
    }

    @Getter
    public enum PageSort {
        POPULAR,
        NEWEST;

        @JsonCreator
        public static PageSort create(String requestValue) {
            return Stream.of(values())
                    .filter(v -> v.toString().equalsIgnoreCase(requestValue))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("잘못된 정렬 방식입니다."));
        }
    }
}
