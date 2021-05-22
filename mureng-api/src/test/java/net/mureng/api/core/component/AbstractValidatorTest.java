package net.mureng.api.core.component;

import org.junit.jupiter.api.BeforeEach;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;

public abstract class AbstractValidatorTest {
    protected Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}
