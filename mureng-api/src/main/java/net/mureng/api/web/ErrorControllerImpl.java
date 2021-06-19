package net.mureng.api.web;

import net.mureng.api.core.dto.ApiResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ApiIgnore
@RestController
public class ErrorControllerImpl implements ErrorController {

    @RequestMapping("/error")
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
}
