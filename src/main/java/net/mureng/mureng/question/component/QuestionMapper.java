package net.mureng.mureng.question.component;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.entity.MemberSetting;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.dto.WordHintDto;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.entity.WordHint;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionMapper extends EntityMapper {
    private final WordHintMapper wordHintMapper;

    @Override
    protected void initEntityToDtoMapping() {
        final Converter<Set<WordHint>, Set<WordHintDto>> wordHintConverter = context -> {
            if (context == null)
                return null;

            return context.getSource().stream()
                    .map(wordHintMapper::map)
                    .collect(Collectors.toSet());
        };
        modelMapper.createTypeMap(Question.class, QuestionDto.class)
                .addMappings(mapper -> mapper.using(wordHintConverter)
                        .map(Question::getWordHints, QuestionDto::setWordHints));
    }

    @Override
    protected void initDtoToEntityMapping() {
        modelMapper.createTypeMap(QuestionDto.class, Question.class)
                .addMappings(mapper -> mapper.skip(Question::setMember))
                .addMappings(mapper -> mapper.skip(Question::setRegDate));
    }

    public Question map(QuestionDto questionDto) {
        return modelMapper.map(questionDto, Question.class);
    }

    public QuestionDto map(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }
}
