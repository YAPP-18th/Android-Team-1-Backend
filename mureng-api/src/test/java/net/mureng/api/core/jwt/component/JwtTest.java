package net.mureng.api.core.jwt.component;

import net.mureng.core.core.component.DateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;

import static org.mockito.Mockito.when;

/**
 * 2080 년과 같이 먼 미래로 설정한 이유는 JWT Claim 파싱 시 유효기간이 지나면 ExpiredJwtException을 발생시키기 때문
 */
@ExtendWith(MockitoExtension.class)
abstract class JwtTest {
    protected static final String TEST_SECRET_KEY = "testKey";

    /**
     * 2080-10-21 11:58:30 기준 + 24시간 생성
     */
    protected static final String TEST_ACCESS_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJuaWNrbmFtZSI6IlRlc3QiL" +
            "CJpYXQiOjM0OTY3MDUxMTAsImV4cCI6MzQ5Njc5MTUxMH0.zzKLUCSfCPgseM9wbuTOFnKWTI6rSRzkL0BBruBe6P93iKjxh4y6vxAMV" +
            "zF_LOZqdCKCUF9WWUnNX--xru1EZQ";

    /**
     * 2080-10-21 11:58:30 기준 + 2달 생성
     */
    protected static final String TEST_REFRESH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJuaWNrbmFtZSI6IlRlc3Qi" +
            "LCJpYXQiOjM0OTY3MDUxMTAsImV4cCI6MzUwMTg4OTExMH0.rRluGJ0MlzXCMAZcgEcHhW9h-QgqEtAegjk_CXDn2HK_hJihly1Xkr4u" +
            "m9CgpmTpQOFS5RHqBrF12FdaXFyz0A";
}