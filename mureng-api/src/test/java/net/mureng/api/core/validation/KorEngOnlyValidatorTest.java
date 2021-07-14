package net.mureng.api.core.validation;

import net.mureng.api.core.validation.annotation.KorEngOnly;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class KorEngOnlyValidatorTest extends AbstractValidatorTest {
    @Test
    public void 한글영어문자열_검증_단순_테스트() {
        KorEngOnlyValidator validator = new KorEngOnlyValidator();
        assertTrue(validator.isValid("한글만된다", null));
        assertTrue(validator.isValid("canbeenglish", null));
        assertTrue(validator.isValid("Canbeboth둘다된다", null));
        assertTrue(validator.isValid("한글english1234", null));
        assertFalse(validator.isValid("얘는안된다;;", null));
        assertFalse(validator.isValid("notthistoo~.~", null));
        assertFalse(validator.isValid("해보니까 공백넣는 것도 안됨", null));
    }

    @Test
    public void 한글영어문자열_검증_심화_테스트() {
        SimpleDto simpleDto = new SimpleDto("한글english1234");
        Set<ConstraintViolation<SimpleDto>> violations = validator.validate(simpleDto);
        assertTrue(violations.isEmpty());

        simpleDto = new SimpleDto("얘는 안된다;;");
        violations = validator.validate(simpleDto);
        assertFalse(violations.isEmpty());

        simpleDto = new SimpleDto("not this too ~.~");
        violations = validator.validate(simpleDto);
        assertFalse(violations.isEmpty());
    }

    private static class SimpleDto {
        SimpleDto(String value) {
            this.value = value;
        }

        @KorEngOnly
        private final String value;

        public String getValue() {
            return value;
        }
    }
}