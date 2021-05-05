package net.mureng.mureng.core.advice;

import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.exception.MurengException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;

@Priority(5)
@RestControllerAdvice
public class ValidationControllerAdvice extends AbstractControllerAdvice {
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApiResult<?>> handleValidationException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        String message = String.format("[%s : %s]",
                fieldError.getField(), fieldError.getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.fail(message));
    }
}
