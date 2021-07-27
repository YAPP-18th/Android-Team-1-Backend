package net.mureng.api.core.advice;

import net.mureng.api.core.dto.ApiResult;
import net.mureng.core.core.exception.business.MurengException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;

@Priority(20)
@RestControllerAdvice
public class BusinessControllerAdvice extends AbstractControllerAdvice{

    @ExceptionHandler(value = { MurengException.class })
    protected ResponseEntity<ApiResult<?>> handleMurengException(MurengException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.fail(e.getMessage()));
    }
}
