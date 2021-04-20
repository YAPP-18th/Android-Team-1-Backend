package net.mureng.mureng.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mureng.mureng.core.dto.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/test")
    public ResponseEntity<ApiResult<TestObject>> test() {
        return ResponseEntity.ok(ApiResult.ok(new TestObject("test is ok!!!")));
    }

    @AllArgsConstructor
    @Getter @Setter
    public static class TestObject {
        private String result;
    }
}
