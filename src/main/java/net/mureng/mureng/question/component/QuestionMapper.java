package net.mureng.mureng.question.component;

import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.entity.MemberSetting;
import net.mureng.mureng.question.dto.QuestionDto;
import net.mureng.mureng.question.entity.Question;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class QuestionMapper extends EntityMapper {
    @Override
    protected void initEntityToDtoMapping() {
        modelMapper.createTypeMap(Question.class, QuestionDto.class);
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
