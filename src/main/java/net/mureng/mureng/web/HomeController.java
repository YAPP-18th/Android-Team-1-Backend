package net.mureng.mureng.web;

import net.mureng.mureng.core.dto.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/test")
    public ApiResult<?> test() {
        return ApiResult.ok("test is ok!!");
    }
}
