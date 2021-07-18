package net.mureng.api.core.validation;

import net.mureng.api.core.validation.annotation.KorEngOnly;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 한글, 숫자, 영어만 허용한다. 한글의 경우 자음, 모음 만 사용하는건 허용 안함
 */
public class KorEngOnlyValidator implements ConstraintValidator<KorEngOnly, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       return Pattern.matches("^[가-힣0-9a-zA-Z]*$", value);
    }
}
