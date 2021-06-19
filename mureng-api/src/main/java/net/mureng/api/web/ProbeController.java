package net.mureng.api.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.mureng.api.core.dto.ApiResult;
import net.mureng.core.core.exception.MurengException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/") // HealthCheck를 위한 것으로, 큰 의미 없음!!
public class ProbeController {

    @GetMapping("")
    public ResponseEntity<ApiResult<TestObject>> test() {
        return ResponseEntity.ok(ApiResult.ok(new TestObject("test is ok!!!")));
    }

    @AllArgsConstructor
    @Getter @Setter
    public static class TestObject {
        private String result;
    }
}
