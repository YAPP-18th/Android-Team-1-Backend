package net.mureng.api.core.advice;

import lombok.extern.slf4j.Slf4j;
import net.mureng.api.core.dto.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public abstract class AbstractControllerAdvice {
    protected ResponseEntity<ApiResult<?>> handleException(Throwable e, HttpStatus status) {
        log.error(e.getMessage(), e);
        return handleException(e, status, status.value());
    }

    protected ResponseEntity<ApiResult<?>> handleException(Throwable e, HttpStatus status, int errorCode) {
        return ResponseEntity.status(status)
                .body(ApiResult.fail(e.getMessage()));
    }
}
