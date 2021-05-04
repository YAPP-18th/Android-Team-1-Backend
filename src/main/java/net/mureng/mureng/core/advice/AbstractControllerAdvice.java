package net.mureng.mureng.core.advice;

import lombok.extern.slf4j.Slf4j;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.exception.AccessNotAllowedException;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.core.exception.MurengException;
import net.mureng.mureng.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
