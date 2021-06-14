package net.mureng.api.core.jwt.component;

import net.mureng.core.common.EntityCreator;
import net.mureng.core.core.component.DateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtCreatorTest extends JwtTest {
    @InjectMocks
    private JwtCreator jwtCreator;

    @Mock
    private DateFactory dateFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtCreator, "secretKey", TEST_SECRET_KEY);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.set(2080, Calendar.OCTOBER, 21, 11, 58, 30);
        when(dateFactory.now()).thenReturn(calendar.getTime());
    }

    @Test
    public void 액세스_토큰_생성_테스트() {
        assertEquals(TEST_ACCESS_TOKEN, jwtCreator.createAccessToken(EntityCreator.createMemberEntity()));;
    }

    @Test
    public void 리프레시_토큰_생성_테스트() {
        assertEquals(TEST_REFRESH_TOKEN, jwtCreator.createRefreshToken(EntityCreator.createMemberEntity()));;
    }
}