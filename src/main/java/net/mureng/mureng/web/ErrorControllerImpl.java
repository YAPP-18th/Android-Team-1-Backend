package net.mureng.mureng.web;

import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.exception.AccessNotAllowedException;
import net.mureng.mureng.core.exception.MurengException;
import net.mureng.mureng.core.exception.ResourceNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ApiIgnore
@RestController
public class ErrorControllerImpl implements ErrorController {

    @GetMapping("/error")
    public ResponseEntity<?> error(HttpServletRequest request) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusObj != null) {
            int statusCode = Integer.parseInt(statusObj.toString());
            HttpStatus status = HttpStatus.valueOf(statusCode);
            return ResponseEntity.status(status)
                    .body(ApiResult.fail(status.name()));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.fail("Internal Server Error"));
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
