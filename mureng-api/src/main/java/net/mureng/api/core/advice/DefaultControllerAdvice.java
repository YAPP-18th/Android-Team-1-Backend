package net.mureng.api.core.advice;

import net.mureng.api.core.dto.ApiResult;
import net.mureng.core.core.exception.AccessNotAllowedException;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.core.exception.MurengException;
import net.mureng.core.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;

@Priority(20)
@RestControllerAdvice
public class DefaultControllerAdvice extends AbstractControllerAdvice {
    @ExceptionHandler(value = { Exception.class, MurengException.class })
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
}
