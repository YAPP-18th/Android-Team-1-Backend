package net.mureng.api.core.jwt.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import net.mureng.api.core.dto.ApiPageRequest;
import net.mureng.core.core.exception.BadRequestException;

import java.util.stream.Stream;

@Getter
public enum TokenProvider {
    KAKAO,
    GOOGLE;

    @JsonCreator
    public static TokenProvider create(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.toString().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("잘못된 프로바이더입니다."));
    }
}
