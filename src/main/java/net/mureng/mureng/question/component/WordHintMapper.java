package net.mureng.mureng.question.component;

import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.dto.WordHintDto;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.entity.WordHint;
import org.springframework.stereotype.Component;

@Component
public class WordHintMapper extends EntityMapper {
    @Override
    protected void initEntityToDtoMapping() {
        modelMapper.createTypeMap(WordHint.class, WordHintDto.class);
    }

    @Override
    protected void initDtoToEntityMapping() {
        modelMapper.createTypeMap(WordHintDto.class, WordHint.class)
                .addMappings(mapper -> mapper.skip(WordHint::setQuestion))
                .addMappings(mapper -> mapper.skip(WordHint::setRegDate));
    }

    public WordHint map(WordHintDto questionDto) {
        return modelMapper.map(questionDto, WordHint.class);
    }

    public WordHintDto map(WordHint question) {
        return modelMapper.map(question, WordHintDto.class);
    }
}
