package net.mureng.mureng.core.advice;

import lombok.extern.slf4j.Slf4j;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.exception.AccessNotAllowedException;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.core.exception.ResourceNotFoundException;
import net.mureng.mureng.core.exception.MurengException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DefaultControllerAdvice {
    @ExceptionHandler(value = {Exception.class, MurengException.class})
    public ResponseEntity<ApiResult<?>> handleUnknownException(Exception e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { BadRequestException.class })
    public ResponseEntity<ApiResult<?>> handleBadRequestException(Exception e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class})
    public ResponseEntity<ApiResult<?>> handleNotFoundException(Exception e) {
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { AccessNotAllowedException.class})
    public ResponseEntity<ApiResult<?>> handleForbiddenException(Exception e) {
        return handleException(e, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<ApiResult<?>> handleException(Throwable e, HttpStatus status) {
        log.error(e.getMessage(), e);
        return handleException(e, status, status.value());
    }

    private ResponseEntity<ApiResult<?>> handleException(Throwable e, HttpStatus status, int errorCode) {
        return ResponseEntity.status(status)
                .body(ApiResult.fail(e.getMessage()));
    }
}
