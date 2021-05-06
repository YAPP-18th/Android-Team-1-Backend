package net.mureng.mureng.member.component;

import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.entity.MemberSetting;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class MemberMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    protected MemberMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        initEntityToDtoMapping();
        initDtoToEntityMapping();

        modelMapper.validate();
    }

    private void initEntityToDtoMapping() {
        modelMapper.createTypeMap(Member.class, MemberDto.class)
                .addMapping(src -> src.getMemberAttendance().getAttendanceCount(), MemberDto::setAttendanceCount)
                .addMapping(src -> src.getMemberAttendance().getLastAttendanceDate(), MemberDto::setLastAttendanceDate)
                .addMapping(src -> src.getMemberSetting().isPushActive(), MemberDto::setPushActive);
    }

    private void initDtoToEntityMapping() {
        final Converter<MemberDto, MemberAttendance> memberAttendanceConverter = context -> {
            if (context == null)
                return null;

            return MemberAttendance.builder()
                    .attendanceCount(context.getSource().getAttendanceCount())
                    .lastAttendanceDate(LocalDate.parse(context.getSource().getLastAttendanceDate()))
                    .build();
        };

        final Converter<MemberDto, MemberSetting> memberSettingConverter = context -> {
            if (context == null)
                return null;

            return MemberSetting.builder()
                    .isPushActive(context.getSource().isPushActive())
                    .build();
        };

        modelMapper.createTypeMap(MemberDto.class, Member.class)
                .addMappings(mapper -> mapper.using(memberAttendanceConverter)
                        .map(source -> source, Member::setMemberAttendance))
                .addMappings(mapper -> mapper.using(memberSettingConverter)
                        .map(source -> source, Member::setMemberSetting))
                .addMappings(mapper -> mapper.skip(Member::setRegDate))
                .addMappings(mapper -> mapper.skip(Member::setModDate))
                .addMappings(mapper -> mapper.skip(Member::setActive));
    }

    public Member map(MemberDto memberDto) {
        return modelMapper.map(memberDto, Member.class);
    }

    public MemberDto map(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }
}
