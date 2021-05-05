package net.mureng.mureng.core.validation;

import net.mureng.mureng.core.validation.annotation.DateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateStringValidator implements ConstraintValidator<DateFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
