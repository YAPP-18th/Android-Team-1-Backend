package net.mureng.mureng.member.component;

import net.mureng.mureng.core.component.EntityMapper;
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
public class MemberMapper extends EntityMapper {
    protected void initEntityToDtoMapping() {
        modelMapper.createTypeMap(Member.class, MemberDto.class)
                .addMapping(src -> src.getMemberAttendance().getAttendanceCount(), MemberDto::setAttendanceCount)
                .addMapping(src -> src.getMemberAttendance().getLastAttendanceDate(), MemberDto::setLastAttendanceDate)
                .addMapping(src -> src.getMemberSetting().isPushActive(), MemberDto::setPushActive);
    }

    protected void initDtoToEntityMapping() {
        modelMapper.createTypeMap(MemberDto.class, Member.class)
                .addMappings(mapper -> mapper.skip(Member::setMemberAttendance))
                .addMappings(mapper -> mapper.skip(Member::setMemberSetting))
                .addMappings(mapper -> mapper.skip(Member::setMurengCount))
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
