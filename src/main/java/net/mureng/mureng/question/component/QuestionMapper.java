package net.mureng.mureng.question.component;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.dto.WordHintDto;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.entity.WordHint;
import net.mureng.mureng.reply.entity.Reply;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
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
        final Converter<List<Reply>, Long> repliesCountConverter = context -> {
            if (context == null)
                return null;

            return context.getSource().stream().count();
        };

        modelMapper.createTypeMap(Question.class, QuestionDto.class)
                .addMappings(mapper -> mapper.using(wordHintConverter)
                        .map(Question::getWordHints, QuestionDto::setWordHints))
                .addMappings(mapper -> mapper.using(repliesCountConverter)
                        .map(Question::getReplies, QuestionDto::setRepliesCount));
    }

    @Override
    protected void initDtoToEntityMapping() {
        modelMapper.createTypeMap(QuestionDto.class, Question.class)
                .addMappings(mapper -> mapper.skip(Question::setMember))
                .addMappings(mapper -> mapper.skip(Question::setRegDate))
                .addMappings(mapper -> mapper.skip(Question::setReplies));
    }

    public Question map(QuestionDto questionDto) {
        return modelMapper.map(questionDto, Question.class);
    }

    public QuestionDto map(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }
}
