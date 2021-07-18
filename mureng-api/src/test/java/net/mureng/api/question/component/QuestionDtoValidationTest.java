package net.mureng.api.question.component;


import net.mureng.api.core.validation.AbstractValidatorTest;
import net.mureng.api.question.dto.QuestionDto;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionDtoValidationTest extends AbstractValidatorTest {

    @Test
    public void 질문DTO_검증_테스트() { // TODO 좀 더 직관적으로 만들어보기
        QuestionDto questionDto = QuestionDto.builder()
                .content("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzaaaaaaaaqaaaaaaaaaaaa") // 73자
                .koContent("가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다") // 45자
                .build();
        Set<ConstraintViolation<QuestionDto>> violations = validator.validate(questionDto);
        assertTrue(violations.isEmpty());

        questionDto = QuestionDto.builder()
                .content("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzaaaaaaaaqaaaaaaaaaaaa") // 73자
                .build();
        violations = validator.validate(questionDto);
        assertTrue(violations.isEmpty());

        questionDto = QuestionDto.builder()
                .content("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzaaaaaaaaqaaaaaaaaaaaaa") // 74자 - 위반
                .koContent("가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다") // 45자
                .build();
        violations = validator.validate(questionDto);
        assertFalse(violations.isEmpty());

        questionDto = QuestionDto.builder()
                .content("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzaaaaaaaaqaaaaaaaaaaaa") // 73자
                .koContent("가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라") // 46자 - 위반
                .build();
        violations = validator.validate(questionDto);
        assertFalse(violations.isEmpty());
    }
}