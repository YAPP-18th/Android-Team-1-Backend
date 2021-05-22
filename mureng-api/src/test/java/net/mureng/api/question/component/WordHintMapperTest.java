package net.mureng.api.question.component;

import net.mureng.api.question.dto.WordHintDto;
import net.mureng.core.question.entity.Question;
import net.mureng.core.question.entity.WordHint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WordHintMapperTest {
    @Autowired
    WordHintMapper wordHintMapper;

    private final WordHint wordHint = WordHint.builder()
            .hintId(1L)
            .question(Question.builder().build())
            .word("apple")
            .meaning("사과")
            .regDate(LocalDateTime.parse("2020-10-14T11:00:00"))
            .build();

    private final WordHintDto.ReadOnly wordHintDto = WordHintDto.ReadOnly.builder()
            .hintId(1L)
            .word("apple")
            .meaning("사과")
            .build();

    @Test
    public void 엔티티에서_DTO변환_테스트() {
        WordHintDto.ReadOnly mappedDto = wordHintMapper.toDto(wordHint);
        assertEquals(wordHintDto.getHintId(), mappedDto.getHintId());
        assertEquals(wordHintDto.getWord(), mappedDto.getWord());
        assertEquals(wordHintDto.getMeaning(), mappedDto.getMeaning());
    }

    @Test
    public void DTO에서_엔티티변환_테스트() {
        WordHint mappedEntity = wordHintMapper.toEntity(wordHintDto);
        assertEquals(wordHint.getWord(), mappedEntity.getWord());
        assertEquals(wordHint.getMeaning(), mappedEntity.getMeaning());
    }
}