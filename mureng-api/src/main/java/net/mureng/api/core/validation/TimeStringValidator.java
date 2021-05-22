package net.mureng.api.core.validation;

import net.mureng.api.core.validation.annotation.TimeFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class TimeStringValidator implements ConstraintValidator<TimeFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalTime.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
