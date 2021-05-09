package net.mureng.mureng.core.component;

import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.entity.Question;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public abstract class EntityMapper {
    protected final ModelMapper modelMapper = new ModelMapper();

    protected EntityMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        initEntityToDtoMapping();
        initDtoToEntityMapping();

        modelMapper.validate();
    }

    protected abstract void initEntityToDtoMapping();
    protected abstract void initDtoToEntityMapping();
}
