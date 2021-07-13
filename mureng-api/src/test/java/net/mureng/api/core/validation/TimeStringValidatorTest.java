package net.mureng.api.core.validation;


import net.mureng.api.core.validation.annotation.TimeFormat;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeStringValidatorTest extends AbstractValidatorTest {
    @Test
    public void 시간문자열_검증_단순_테스트() {
        TimeStringValidator validator = new TimeStringValidator();
        assertTrue(validator.isValid("11:23:34", null));
        assertFalse(validator.isValid("111:23:34", null));
        assertFalse(validator.isValid("23:61:44", null));
    }

    @Test
    public void 시간문자열_검증_심화_테스트() {
        SimpleDto simpleDto = new SimpleDto("11:23:34");
        Set<ConstraintViolation<SimpleDto>> violations = validator.validate(simpleDto);
        assertTrue(violations.isEmpty());

        simpleDto = new SimpleDto("111:23:34");
        violations = validator.validate(simpleDto);
        assertFalse(violations.isEmpty());

        simpleDto = new SimpleDto("23:61:44");
        violations = validator.validate(simpleDto);
        assertFalse(violations.isEmpty());
    }

    private static class SimpleDto {
        SimpleDto(String timeString) {
            this.timeString = timeString;
        }

        @TimeFormat
        private final String timeString;

        public String getTimeString() {
            return timeString;
        }
    }
}