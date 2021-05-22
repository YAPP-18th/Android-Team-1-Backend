package net.mureng.api.core.component;


import net.mureng.api.core.validation.annotation.DateFormat;
import net.mureng.api.core.validation.DateStringValidator;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DateStringValidatorTest extends AbstractValidatorTest {
    @Test
    public void 날짜문자열_검증_단순_테스트() {
        DateStringValidator validator = new DateStringValidator();
        assertTrue(validator.isValid("2020-10-14", null));
        assertFalse(validator.isValid("20202-10-14", null));
        assertFalse(validator.isValid("2020-13-14", null));
    }

    @Test
    public void 날짜문자열_검증_심화_테스트() {
        SimpleDto simpleDto = new SimpleDto("2020-10-14");
        Set<ConstraintViolation<SimpleDto>> violations = validator.validate(simpleDto);
        assertTrue(violations.isEmpty());

        simpleDto = new SimpleDto("20202-10-14");
        violations = validator.validate(simpleDto);
        assertFalse(violations.isEmpty());

        simpleDto = new SimpleDto("2020-13-14");
        violations = validator.validate(simpleDto);
        assertFalse(violations.isEmpty());
    }

    private static class SimpleDto {
        SimpleDto(String dateString) {
            this.dateString = dateString;
        }

        @DateFormat
        private final String dateString;

        public String getDateString() {
            return dateString;
        }
    }
}